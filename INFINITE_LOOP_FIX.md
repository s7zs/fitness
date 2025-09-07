# Infinite Loop Fix - my-meals Endpoint

## Problem
The `/auth/nutrition/my-meals` endpoint was causing an infinite loop when called. This was due to a circular reference in the JSON serialization between the `nutritionplan` and `meal` entities.

## Root Cause
The bidirectional `@ManyToMany` relationship between `nutritionplan` and `meal` entities was causing Jackson to get stuck in an infinite loop during JSON serialization:

1. `nutritionplan` contains a `Set<meal> meals`
2. Each `meal` contains a `Set<nutritionplan> nutritionplans`
3. When Jackson tries to serialize a `nutritionplan`, it includes the `meals`
4. When Jackson tries to serialize each `meal`, it includes the `nutritionplans`
5. This creates an infinite loop: nutritionplan → meals → nutritionplans → meals → ...

## Fix Applied

### 1. **Added JSON Annotations to Break Circular Reference**

**nutritionplan.java:**
```java
@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
@JoinTable(
    name = "nutrition_plan_meals",
    joinColumns = @JoinColumn(name = "nutrition_plan_id"),
    inverseJoinColumns = @JoinColumn(name = "meal_id")
)
@JsonManagedReference  // This side will be serialized
private Set<meal> meals = new HashSet<>();
```

**meal.java:**
```java
@ManyToMany(mappedBy = "meals", fetch = FetchType.LAZY)
@JsonBackReference  // This side will be ignored during serialization
private Set<nutritionplan> nutritionplans = new HashSet<>();
```

### 2. **Created MealResponse DTO**

**MealResponse.java:**
```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealResponse {
    private long meal_id;
    private String meal_name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date time;
    
    private double gramofcarb;
    private double gramofprotien;
    private double gramoffat;
}
```

### 3. **Updated Controller to Use DTO**

**nutritioncontroller.java:**
```java
@GetMapping("/my-meals")
public ResponseEntity<?> getMyMeals() {
    try {
        if (!nutritionService.hasNutritionPlan()) {
            return ResponseEntity.badRequest().body("No nutrition plan assigned yet. Please contact your coach.");
        }

        nutritionplan plan = nutritionService.getCurrentUserNutritionPlan();
        
        // Convert meals to DTOs to avoid circular reference
        Set<MealResponse> mealResponses = plan.getMeals().stream()
                .map(this::convertToMealResponse)
                .collect(Collectors.toSet());
        
        return ResponseEntity.ok(mealResponses);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error retrieving meals: " + e.getMessage());
    }
}

private MealResponse convertToMealResponse(meal meal) {
    MealResponse response = new MealResponse();
    response.setMeal_id(meal.getMeal_id());
    response.setMeal_name(meal.getMeal_name());
    response.setTime(meal.getTime());
    response.setGramofcarb(meal.getGramofcarb());
    response.setGramofprotien(meal.getGramofprotien());
    response.setGramoffat(meal.getGramoffat());
    return response;
}
```

## How the Fix Works

### **@JsonManagedReference and @JsonBackReference**
- `@JsonManagedReference`: This side of the relationship will be serialized normally
- `@JsonBackReference`: This side will be ignored during serialization, breaking the circular reference

### **DTO Pattern**
- The `MealResponse` DTO contains only the necessary meal data
- No bidirectional relationships, preventing circular references
- Clean, controlled JSON output

## Expected Results

✅ **No More Infinite Loop**: The `/auth/nutrition/my-meals` endpoint will now return a proper JSON response

✅ **Clean JSON Output**: The response will contain only meal data without circular references

✅ **Better Performance**: Faster serialization without infinite loops

✅ **Maintainable Code**: Clear separation between entities and DTOs

## Testing

You can now test the endpoint:

```bash
GET http://localhost:8080/auth/nutrition/my-meals
Authorization: Bearer <your-jwt-token>
```

**Expected Response:**
```json
[
  {
    "meal_id": 1,
    "meal_name": "Breakfast",
    "time": "2024-01-01",
    "gramofcarb": 50.0,
    "gramofprotien": 30.0,
    "gramoffat": 20.0
  },
  {
    "meal_id": 2,
    "meal_name": "Lunch",
    "time": "2024-01-01",
    "gramofcarb": 60.0,
    "gramofprotien": 40.0,
    "gramoffat": 25.0
  }
]
```

The infinite loop issue should now be completely resolved! 🎉
