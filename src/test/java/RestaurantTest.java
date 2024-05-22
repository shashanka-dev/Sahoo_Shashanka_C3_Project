
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;
    Restaurant spyRestaurant;

    @BeforeEach
    public void setup () {
        restaurant = new Restaurant("Dalma","Salt Lake",LocalTime.parse("12:00"),LocalTime.parse("22:30"));
        restaurant.addToMenu("Paneer Dopiyaza",129);
        restaurant.addToMenu("Kashmiri Pulao",129);
        restaurant.addToMenu("Mushroom Masala",129);

        spyRestaurant = spy(restaurant);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {

        doReturn(LocalTime.parse("22:00")).when(spyRestaurant).getCurrentTime();

        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {

        doReturn(LocalTime.parse("11:00")).when(spyRestaurant).getCurrentTime();

        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<CHECK ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void getOrderValue_should_return_total_amount_of_the_provided_list_of_items () throws itemNotFoundException {

        ArrayList<String> selectedItems = new ArrayList<>();
        selectedItems.add("Paneer Dopiyaza");//129
        selectedItems.add("Kashmiri Pulao"); //129
        selectedItems.add("Mushroom Masala"); //129

        int totalVal = restaurant.getTotalItemValue(selectedItems);

        assertEquals(387, totalVal);
    }

    @Test
    public void getOrderValue_should_throw_itemNotFoundException_for_provided_items_not_present_in_menu () throws itemNotFoundException {

        ArrayList<String> selectedItems = new ArrayList<>();
        selectedItems.add("Paneer Dopiyaza");
        selectedItems.add("Chilly Chicken");
        selectedItems.add("Mushroom Masala");

        assertThrows(itemNotFoundException.class,()->{
            restaurant.getTotalItemValue(selectedItems);
        });
    }
    //<<<<<<<<<<<<<<<<<<<<CHECK ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>
}