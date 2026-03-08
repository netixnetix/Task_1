package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static praktikum.IngredientType.FILLING;
import static praktikum.IngredientType.SAUCE;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BurgerTest {

    private static final String CHOP_NAME = "Базовая добавка";
    private static final String FIRST_BUN_NAME = "Белая булочка";
    private static final String SECOND_BUN_NAME = "Черная булочка";
    private static final String FILLING_NAME_CHEESE = "российский сыр";
    private static final String FILLING_NAME_MEAT = "говяжья котлета";
    private static final String INGREDIENT_NAME_A = "сыр";
    private static final String INGREDIENT_NAME_B = "котлета";
    private static final String INGREDIENT_NAME_REMOVED = "Сыр";
    private static final String FIRST_FILLING = "котлета";
    private static final String SECOND_FILLING = "огурцы";
    private static final String SAUCE_NAME_FOR_MOVE = "томатный соус";
    private static final String SAUCE_NAME = "чесночный соус";
    private static final String SAUCE_NAME_SECOND = "цезарь соус";
    private static final String BUN_NAME_FOR_RECEIPT = "Белый";
    private static final String DEFAULT_BUN_NAME = "Bun";

    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockIngredient;

    private Burger burger;

    @BeforeEach
    void setUp() {
        burger = new Burger();
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockBun.getName()).thenReturn(DEFAULT_BUN_NAME);
        burger.setBuns(mockBun);
    }

    @Test
    @DisplayName("Цена бургера без ингредиентов равна удвоенной цене булочки")
    void setsBunAndItIsUsedInGetPrice() {
        assertEquals(200f, burger.getPrice());
    }

    @Test
    @DisplayName("После добавления одного ингредиента размер списка равен 1")
    void addIngredient_increasesIngredientsSizeToOne() {
        burger.addIngredient(mockIngredient);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    @DisplayName("Список ингредиентов содержит добавленный ингредиент")
    void addIngredient_listContainsAddedIngredient() {
        burger.addIngredient(mockIngredient);
        assertTrue(burger.ingredients.contains(mockIngredient));
    }

    @Test
    @DisplayName("После удаления единственного ингредиента список пуст")
    void removeIngredient_emptiesListWhenOnlyOneRemoved() {
        burger.addIngredient(mockIngredient);
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    @DisplayName("После замены булочки в чеке отображается новая булочка")
    void setBuns_receiptContainsNewBunName() {
        Bun firstBun = new Bun(FIRST_BUN_NAME, 50f);
        Bun secondBun = new Bun(SECOND_BUN_NAME, 80f);
        burger.setBuns(firstBun);
        burger.setBuns(secondBun);
        assertTrue(burger.getReceipt().contains(SECOND_BUN_NAME));
    }

    @Test
    @DisplayName("После замены булочки в чеке не отображается старая булочка")
    void setBuns_receiptDoesNotContainOldBunName() {
        Bun firstBun = new Bun(FIRST_BUN_NAME, 50f);
        Bun secondBun = new Bun(SECOND_BUN_NAME, 80f);
        burger.setBuns(firstBun);
        burger.setBuns(secondBun);
        assertFalse(burger.getReceipt().contains(FIRST_BUN_NAME));
    }

    @Test
    @DisplayName("Добавленный ингредиент присутствует в списке")
    void addIngredient_addsOneIngredientToList() {
        Ingredient chop = new Ingredient(FILLING, CHOP_NAME, 150f);
        burger.addIngredient(chop);
        assertTrue(burger.ingredients.contains(chop));
    }

    @Test
    @DisplayName("Первый добавленный ингредиент находится по индексу 0")
    void addIngredient_firstIngredientAtIndexZero() {
        Ingredient chopCheese = new Ingredient(FILLING, FILLING_NAME_CHEESE, 75f);
        Ingredient chopMeat = new Ingredient(FILLING, FILLING_NAME_MEAT, 150f);
        burger.addIngredient(chopCheese);
        burger.addIngredient(chopMeat);
        assertEquals(chopCheese, burger.ingredients.get(0));
    }

    @Test
    @DisplayName("Второй добавленный ингредиент находится по индексу 1")
    void addIngredient_secondIngredientAtIndexOne() {
        Ingredient chopCheese = new Ingredient(FILLING, FILLING_NAME_CHEESE, 75f);
        Ingredient chopMeat = new Ingredient(FILLING, FILLING_NAME_MEAT, 150f);
        burger.addIngredient(chopCheese);
        burger.addIngredient(chopMeat);
        assertEquals(chopMeat, burger.ingredients.get(1));
    }

    @ParameterizedTest
    @EnumSource(IngredientType.class)
    @DisplayName("В списке сохраняется корректный тип ингредиента (SAUCE / FILLING)")
    void addIngredient_listContainsIngredientWithCorrectType(IngredientType type) {
        Ingredient ingredient = new Ingredient(type, CHOP_NAME, 99f);
        burger.addIngredient(ingredient);
        assertEquals(type, burger.ingredients.get(0).getType());
    }

    @Test
    @DisplayName("При добавлении двух ингредиентов метод add вызывается два раза")
    void removeIngredient_spyAddWasCalledTwice() {
        List<Ingredient> realList = new ArrayList<>();
        List<Ingredient> spyList = spy(realList);
        burger.ingredients = spyList;
        Ingredient ing1 = new Ingredient(FILLING, INGREDIENT_NAME_A, 50f);
        Ingredient ing2 = new Ingredient(FILLING, INGREDIENT_NAME_B, 80f);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.removeIngredient(1);
        verify(spyList, times(2)).add(any(Ingredient.class));
    }

    @Test
    @DisplayName("При удалении по индексу метод remove вызывается один раз с этим индексом")
    void removeIngredient_spyRemoveWasCalledOnceWithIndex() {
        List<Ingredient> realList = new ArrayList<>();
        List<Ingredient> spyList = spy(realList);
        burger.ingredients = spyList;
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_A, 50f));
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_B, 80f));
        burger.removeIngredient(1);
        verify(spyList, times(1)).remove(1);
    }

    @Test
    @DisplayName("После удаления одного из двух ингредиентов в списке остаётся один элемент")
    void removeIngredient_spyListSizeIsOneAfterRemove() {
        List<Ingredient> realList = new ArrayList<>();
        List<Ingredient> spyList = spy(realList);
        burger.ingredients = spyList;
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_A, 50f));
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_B, 80f));
        burger.removeIngredient(1);
        assertEquals(1, spyList.size());
    }

    @Test
    @DisplayName("После удаления второго ингредиента первым в списке остаётся первый добавленный")
    void removeIngredient_spyListFirstElementRemainsAfterRemove() {
        List<Ingredient> realList = new ArrayList<>();
        List<Ingredient> spyList = spy(realList);
        burger.ingredients = spyList;
        Ingredient ing1 = new Ingredient(FILLING, INGREDIENT_NAME_A, 50f);
        burger.addIngredient(ing1);
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_B, 80f));
        burger.removeIngredient(1);
        assertEquals(ing1, spyList.get(0));
    }

    @Test
    @DisplayName("После удаления единственного ингредиента список ингредиентов пуст")
    void removeIngredient_listIsEmptyAfterRemovingOnlyIngredient() {
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_REMOVED, 75f));
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    @DisplayName("После удаления по индексу 1 в списке остаётся один ингредиент")
    void removeIngredient_listSizeOneAfterRemovingSecond() {
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_A, 50f));
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_B, 80f));
        burger.removeIngredient(1);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    @DisplayName("После удаления второго ингредиента по индексу первый остаётся на позиции 0")
    void removeIngredient_listKeepsFirstIngredientAfterRemovingSecond() {
        Ingredient ing1 = new Ingredient(FILLING, INGREDIENT_NAME_A, 50f);
        burger.addIngredient(ing1);
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_B, 80f));
        burger.removeIngredient(1);
        assertEquals(ing1, burger.ingredients.get(0));
    }

    @Test
    @DisplayName("После перемещения ингредиента размер списка не меняется")
    void moveIngredient_listSizeUnchanged() {
        Ingredient ing1 = new Ingredient(FILLING, INGREDIENT_NAME_A, 50f);
        Ingredient ing2 = new Ingredient(FILLING, INGREDIENT_NAME_B, 80f);
        Ingredient ing3 = new Ingredient(SAUCE, SAUCE_NAME_FOR_MOVE, 20f);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.addIngredient(ing3);
        burger.moveIngredient(0, 2);
        assertEquals(3, burger.ingredients.size());
    }

    @Test
    @DisplayName("После перемещения ингредиент оказывается на новой позиции в списке")
    void moveIngredient_movedIngredientAtNewIndexInList() {
        Ingredient ing1 = new Ingredient(FILLING, INGREDIENT_NAME_A, 50f);
        Ingredient ing2 = new Ingredient(FILLING, INGREDIENT_NAME_B, 80f);
        Ingredient ing3 = new Ingredient(SAUCE, SAUCE_NAME_FOR_MOVE, 20f);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.addIngredient(ing3);
        burger.moveIngredient(0, 2);
        assertEquals(ing1, burger.ingredients.get(2));
    }


    @Test
    @DisplayName("Чек начинается с имени булочки в формате (==== имя ====)")
    void getReceipt_startsWithBunName() {
        when(mockBun.getName()).thenReturn(BUN_NAME_FOR_RECEIPT);
        burger.setBuns(mockBun);
        assertTrue(burger.getReceipt().startsWith("(==== " + BUN_NAME_FOR_RECEIPT + " ====)"));
    }

    @Test
    @DisplayName("В чеке без ингредиентов строка с булочкой встречается дважды")
    void getReceipt_withNoIngredients_bunLineAppearsTwice() {
        when(mockBun.getName()).thenReturn(BUN_NAME_FOR_RECEIPT);
        burger.setBuns(mockBun);
        String receipt = burger.getReceipt();
        String bunLine = "(==== " + BUN_NAME_FOR_RECEIPT + " ====)";
        assertNotEquals(receipt.indexOf(bunLine), receipt.lastIndexOf(bunLine));
    }

    @Test
    @DisplayName("В чеке без ингредиентов есть строка с ценой")
    void getReceipt_withNoIngredients_containsPriceLine() {
        when(mockBun.getName()).thenReturn(BUN_NAME_FOR_RECEIPT);
        burger.setBuns(mockBun);
        assertTrue(burger.getReceipt().contains(String.format("Price: %f", burger.getPrice())));
    }

    @Test
    @DisplayName("Цена в чеке совпадает с результатом getPrice()")
    void getReceipt_priceLineMatchesGetPrice() {
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_A, 50f));
        burger.addIngredient(new Ingredient(FILLING, INGREDIENT_NAME_B, 80f));
        assertTrue(burger.getReceipt().contains(String.format("Price: %f", burger.getPrice())));
    }

    @Test
    @DisplayName("В чеке есть строка с первым соусом в формате = sauce название =")
    void getReceipt_containsFirstSauceLine() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME_SECOND, 80f));
        assertTrue(burger.getReceipt().contains(String.format("= sauce %s =", SAUCE_NAME)));
    }

    @Test
    @DisplayName("В чеке есть строка со вторым соусом в формате = sauce название =")
    void getReceipt_containsSecondSauceLine() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME_SECOND, 80f));
        assertTrue(burger.getReceipt().contains(String.format("= sauce %s =", SAUCE_NAME_SECOND)));
    }

    @Test
    @DisplayName("В чеке есть строка с первой начинкой в формате = filling название =")
    void getReceipt_containsFirstFillingLine() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(FILLING, FIRST_FILLING, 80f));
        burger.addIngredient(new Ingredient(FILLING, SECOND_FILLING, 80f));
        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(String.format("= filling %s =", FIRST_FILLING)));
    }

    @Test
    @DisplayName("Первая начинка в чеке встречается ровно один раз")
    void getReceipt_firstFillingAppearsExactlyOnce() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(FILLING, FIRST_FILLING, 80f));
        burger.addIngredient(new Ingredient(FILLING, SECOND_FILLING, 80f));
        String receipt = burger.getReceipt();
        String line = String.format("= filling %s =", FIRST_FILLING);
        assertEquals(receipt.indexOf(line), receipt.lastIndexOf(line));
    }

    @Test
    void getReceipt_containsSecondFillingLine() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(FILLING, FIRST_FILLING, 80f));
        burger.addIngredient(new Ingredient(FILLING, SECOND_FILLING, 80f));
        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(String.format("= filling %s =", SECOND_FILLING)));
    }

    @Test
    @DisplayName("Вторая начинка в чеке встречается ровно один раз")
    void getReceipt_secondFillingAppearsExactlyOnce() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(FILLING, FIRST_FILLING, 80f));
        burger.addIngredient(new Ingredient(FILLING, SECOND_FILLING, 80f));
        String receipt = burger.getReceipt();
        String line = String.format("= filling %s =", SECOND_FILLING);
        assertEquals(receipt.indexOf(line), receipt.lastIndexOf(line));
    }

    @Test
    @DisplayName("В чеке есть строка с соусом в формате = sauce название =")
    void getReceipt_containsSauceLine() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(FILLING, FIRST_FILLING, 80f));
        burger.addIngredient(new Ingredient(FILLING, SECOND_FILLING, 80f));
        String receipt = burger.getReceipt();
        assertTrue(receipt.contains(String.format("= sauce %s =", SAUCE_NAME)));
    }

    @Test
    @DisplayName("Соус в чеке встречается ровно один раз")
    void getReceipt_sauceAppearsExactlyOnce() {
        burger.addIngredient(new Ingredient(SAUCE, SAUCE_NAME, 50f));
        burger.addIngredient(new Ingredient(FILLING, FIRST_FILLING, 80f));
        burger.addIngredient(new Ingredient(FILLING, SECOND_FILLING, 80f));
        String receipt = burger.getReceipt();
        String line = String.format("= sauce %s =", SAUCE_NAME);
        assertEquals(receipt.indexOf(line), receipt.lastIndexOf(line));
    }
}
