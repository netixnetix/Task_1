package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;
import static praktikum.IngredientType.FILLING;
import static praktikum.IngredientType.SAUCE;


class IngredientTest {

    @Test
    @DisplayName("getPrice() возвращает цену, переданную в конструктор")
    void getPrice_returnsPriceFromConstructor() {
        Ingredient ingredient = new Ingredient(SAUCE, "getPriceReturnPrice", 200f);
        assertEquals(ingredient.getPrice(), 200f);
    }

    @Test
    @DisplayName("getPrice() возвращает 0 для ингредиента с ценой 0")
    void getPrice_returnsZeroWhenPriceIsZero() {
        Ingredient ingredient = new Ingredient(FILLING, "getPriceReturnPrice", 0f);
        assertEquals(ingredient.getPrice(), 0f);
    }

    @Test
    @DisplayName("getName() возвращает имя, переданное в конструктор")
    void getName_returnsNameFromConstructor() {
        Ingredient ingredient = new Ingredient(SAUCE, "getPriceReturnName", 0f);
        assertEquals("getPriceReturnName", ingredient.getName());
    }

    @ParameterizedTest
    @EnumSource(IngredientType.class)
    @DisplayName("getType() возвращает Тип для ингредиентов/соусов")
    void getType_returnsSauceWhenCreatedWithSauceType(IngredientType type) {
        Ingredient ingredient = new Ingredient(type, "getTypeReturnType", 0f);
        assertEquals(ingredient.getType(), type);
    }
}
