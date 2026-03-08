package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
    }

    @Test
    @DisplayName("availableBuns() не возвращает null")
    void availableBuns_returnsNonNull() {
        assertNotNull(database.availableBuns());
    }

    @Test
    @DisplayName("availableBuns() возвращает непустой список")
    void availableBuns_returnsNonEmptyList() {
        assertFalse(database.availableBuns().isEmpty());
    }

    @Test
    @DisplayName("availableBuns() при повторном вызове возвращает тот же список")
    void availableBuns_returnsSameListOnEachCall() {
        assertSame(database.availableBuns(), database.availableBuns());
    }

    @Test
    @DisplayName("Все элементы availableBuns() являются экземплярами Bun")
    void availableBuns_allElementsAreBunInstances() {
        List<Bun> buns = database.availableBuns();
        for (Bun bun : buns) {
            assertNotNull(bun);
            assertTrue(bun instanceof Bun);
        }
    }

    @Test
    @DisplayName("У каждой булочки непустое имя и неотрицательная цена")
    void availableBuns_eachBunHasNonNullNameAndNonNegativePrice() {
        for (Bun bun : database.availableBuns()) {
            assertNotNull(bun.getName());
            assertTrue(bun.getPrice() >= 0);
        }
    }

    @Test
    @DisplayName("availableIngredients() не возвращает null")
    void availableIngredients_returnsNonNull() {
        assertNotNull(database.availableIngredients());
    }

    @Test
    @DisplayName("availableIngredients() возвращает непустой список")
    void availableIngredients_returnsNonEmptyList() {
        assertFalse(database.availableIngredients().isEmpty());
    }

    @Test
    @DisplayName("availableIngredients() при повторном вызове возвращает тот же список")
    void availableIngredients_returnsSameListOnEachCall() {
        assertSame(database.availableIngredients(), database.availableIngredients());
    }

    @Test
    @DisplayName("Все элементы availableIngredients() являются экземплярами Ingredient")
    void availableIngredients_allElementsAreIngredientInstances() {
        List<Ingredient> ingredients = database.availableIngredients();
        for (Ingredient ingredient : ingredients) {
            assertNotNull(ingredient);
            assertTrue(ingredient instanceof Ingredient);
        }
    }

    @Test
    @DisplayName("У каждого ингредиента непустое имя, валидный тип и неотрицательная цена")
    void availableIngredients_eachHasNonNullNameValidTypeAndNonNegativePrice() {
        for (Ingredient ingredient : database.availableIngredients()) {
            assertNotNull(ingredient.getName());
            assertNotNull(ingredient.getType());
            assertTrue(ingredient.getPrice() >= 0);
        }
    }

    @Test
    @DisplayName("availableBuns() и availableIngredients() возвращают разные списки")
    void availableBuns_and_availableIngredients_returnDifferentLists() {
        assertNotSame(database.availableBuns(), database.availableIngredients());
    }
}
