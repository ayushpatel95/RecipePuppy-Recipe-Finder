# RecipePuppy-Recipe-Finder
In this assignment you will develop “RecipePuppy recipe finder” app. The App displays
recipes from Recipe Puppy (http://www.recipepuppy.com/). This allows you to search
the recipes of the dish you are interested in, with proper ingredients. In this assignment,
you will learn how to parse JSON.
The base URL of the API is: http://www.recipepuppy.com/api/?i=<INGREDIENTS LIST,
COMMA SEPARATED>&q=<DISH NAME>. An example URL is: http://
www.recipepuppy.com/api/?i=onions,garlic&q=omelet .
You can load the URL in codebeautify.org/jsonviewer for better understanding of
JSON you receive.
In this assignment we will build two activities. The first activity is to search, and the
second activity is to display the results of the recipe search.
Search Activity (30 Points)
You need to implement the following:
1. An EditText to put the dish name.
2. A scrollable add ingredients panel which can add up to 5 ingredients in total.
Page 2 of 4
(a)Search Screen (b) Added a list of
ingredients
(c) Loading recipes (d) Display recipes
Figure 1, App Wireframe
 Recipe Puppy
Search
Dish
Add Ingredients
 Recipes
Title: Monterey Turkey Omelet
Ingredients:
butter, eggs, garlic, green pepper, monterey
jack cheese, icons, turkey, water
URL: http://allrecipes.com/recipe/116899/
monterey-turkey-omelet/
Finish
 Recipes
Loading...
 Recipe Puppy
Search
Dish
Add Ingredients
Omelet
Onions
Garlic
3. The panel to add ingredients should be implemented using Dynamic Layout. It
should follow the same pattern you did in the Homework, see figure 1 (b).
4. It should display an empty EditText, and a floating action Add button. Once, you add
one ingredient, the floating action button should be changed from Add to Remove
button. Follow figure 1(b).
5. Then you need to create the URL as: http://www.recipepuppy.com/api/?
i=<INGREDIENTS LIST, COMMA SEPARATED>&q=<DISH NAME>.
6. There should be a Search button. Clicking on that button should take you to the
Display recipes activity where it displays a list of recipes.
Recipes Activity (60 Points)
1. Use an AsyncTask or Thread pool to communicate with the RecipePuppy api and to
parse the result. Do not use the main thread to parse them.
2. In our example, we wanted to search for a Omelet recipe having two key
ingredients: Onions, and Garlic.
3. In received JSON, you will find that there are two levels of the hierarchy. Inside
“array”, you will find “results”. You need to parse all the items in results.
4. Use AsyncTask or Thread pool to retrieve and parse it.
5. You need to implement a ProgressBar to display the progress while it is parsing, see
figure 1(c).
6. Finally display your result. Please follow the instructions:
1. You have four things to display:
1. Title (“title” in JSON)
2. Recipe image (“thumbnail” in JSON)
3. Ingredients (“ingredients” in JSON)
4. URL (“href” in JSON)
2. You need to use a separate AsyncTask when you load the image. Alternatively,
you can use Picasso library to load the image. (http://square.github.io/picasso/).
3. The URL should be clickable, use implicit intents to open it in browsers.
4. Next you need to implement a navigation pane. It will have five buttons.
1. The leftmost button is the indicator of first recipe.
2. The second button indicates the previous recipe.
3. The Finish button is to close the app.
4. The fourth button indicates the next recipe.
5. The rightmost button indicates the last recipe.
5. Corner Cases:
1. Last recipe: clicking on the next button should not take you anywhere. Toast
to confirm that it is the last recipe.
Page 3 of 4
2. First recipe: clicking on the previous button should toast to display that it is
the first recipe.
3. If no recipe is found, return to the main activity and toast to report that there
were no recipes found.
6. Clicking on Finish button should Finish the second activity and return you to the
main activity.
