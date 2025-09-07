# Nutrition Plan API Documentation

This document describes the API endpoints for managing nutrition plans for users in the fitness tracker application.

## Base URL
All nutrition plan endpoints are prefixed with `/auth/nutrition`

## Authentication
All endpoints require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Endpoints

### 1. Create Nutrition Plan for Specific User
**POST** `/auth/nutrition/plan/{userId}`

Creates a new nutrition plan for a specific user.

**Path Parameters:**
- `userId` (Long): The ID of the user to create the nutrition plan for

**Request Body:**
```json
{
  "startdate": "2024-01-01",
  "enddate": "2024-03-01",
  "meals": [
    {
      "meal_name": "Breakfast",
      "time": "2024-01-01",
      "gramofcarb": 50.0,
      "gramofprotien": 30.0,
      "gramoffat": 20.0
    },
    {
      "meal_name": "Lunch",
      "time": "2024-01-01",
      "gramofcarb": 60.0,
      "gramofprotien": 40.0,
      "gramoffat": 25.0
    }
  ]
}
```

**Response:**
```json
{
  "nutritionid": 1,
  "startdate": "2024-01-01",
  "enddate": "2024-03-01",
  "user": {
    "userid": 123,
    "name": "John Doe",
    "email": "john@example.com"
  },
  "meals": [
    {
      "meal_id": 1,
      "meal_name": "Breakfast",
      "time": "2024-01-01",
      "gramofcarb": 50.0,
      "gramofprotien": 30.0,
      "gramoffat": 20.0
    }
  ]
}
```

### 2. Create Nutrition Plan with Existing Meals
**POST** `/auth/nutrition/plan-with-meals/{userId}`

Creates a nutrition plan for a user using existing meal IDs.

**Path Parameters:**
- `userId` (Long): The ID of the user to create the nutrition plan for

**Request Body:**
```json
{
  "startdate": "2024-01-01",
  "enddate": "2024-03-01",
  "mealIds": [1, 2, 3, 4]
}
```

### 3. Update Nutrition Plan for User
**PUT** `/auth/nutrition/plan/{userId}`

Updates an existing nutrition plan for a specific user.

**Path Parameters:**
- `userId` (Long): The ID of the user whose nutrition plan to update

**Request Body:** Same as create nutrition plan

### 4. Get Nutrition Plan by User ID
**GET** `/auth/nutrition/plan/{userId}`

Retrieves the nutrition plan for a specific user.

**Path Parameters:**
- `userId` (Long): The ID of the user

**Response:** Same as create nutrition plan response

### 5. Get Current User's Nutrition Plan
**GET** `/auth/nutrition/my-plan`

Retrieves the nutrition plan for the currently authenticated user.

**Response:** Same as create nutrition plan response

### 6. Get Current User's Meals
**GET** `/auth/nutrition/my-meals`

Retrieves only the meals from the current user's nutrition plan.

**Response:**
```json
[
  {
    "meal_id": 1,
    "meal_name": "Breakfast",
    "time": "2024-01-01",
    "gramofcarb": 50.0,
    "gramofprotien": 30.0,
    "gramoffat": 20.0
  }
]
```

### 7. Add Meal to Current User's Plan
**POST** `/auth/nutrition/add-meal/{mealId}`

Adds a single meal to the current user's nutrition plan.

**Path Parameters:**
- `mealId` (Long): The ID of the meal to add

### 8. Add Multiple Meals to User's Plan
**POST** `/auth/nutrition/add-meals/{userId}`

Adds multiple meals to a specific user's nutrition plan.

**Path Parameters:**
- `userId` (Long): The ID of the user

**Request Body:**
```json
[1, 2, 3, 4]
```

### 9. Check if User Has Nutrition Plan
**GET** `/auth/nutrition/has-plan`

Checks if the current user has a nutrition plan assigned.

**Response:**
```json
true
```

## Error Responses

All endpoints may return the following error responses:

**400 Bad Request:**
```json
"User not found with ID: 123"
"User already has a nutrition plan. Update it instead of creating new one."
"No nutrition plan found for user ID: 123"
"No nutrition plan assigned yet. Please contact your coach."
```

**401 Unauthorized:**
```json
null
```

## Usage Examples

### Example 1: Create a nutrition plan for a user
```bash
curl -X POST "http://localhost:8080/auth/nutrition/plan/123" \
  -H "Authorization: Bearer your-jwt-token" \
  -H "Content-Type: application/json" \
  -d '{
    "startdate": "2024-01-01",
    "enddate": "2024-03-01",
    "meals": [
      {
        "meal_name": "Breakfast",
        "time": "2024-01-01",
        "gramofcarb": 50.0,
        "gramofprotien": 30.0,
        "gramoffat": 20.0
      }
    ]
  }'
```

### Example 2: Get current user's nutrition plan
```bash
curl -X GET "http://localhost:8080/auth/nutrition/my-plan" \
  -H "Authorization: Bearer your-jwt-token"
```

### Example 3: Add meals to existing plan
```bash
curl -X POST "http://localhost:8080/auth/nutrition/add-meals/123" \
  -H "Authorization: Bearer your-jwt-token" \
  -H "Content-Type: application/json" \
  -d '[1, 2, 3, 4]'
```

## Notes

1. **One-to-One Relationship**: Each user can have only one nutrition plan. If you try to create a second plan, you'll get an error message suggesting to update the existing one.

2. **Date Format**: All dates should be in `yyyy-MM-dd` format.

3. **Meal Validation**: 
   - Carbohydrates: 0.1 - 50.9 grams
   - Protein: 0.1 - 50.9 grams  
   - Fat: 0.1 - 50.9 grams
   - Meal name: 5-100 characters

4. **Transaction Safety**: All operations are wrapped in database transactions to ensure data consistency.

5. **Authentication**: All endpoints require a valid JWT token in the Authorization header.
