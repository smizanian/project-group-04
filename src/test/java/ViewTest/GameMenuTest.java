package ViewTest;

import Controller.CombatController;
import Controller.GameMenuController;
import Database.GameDatabase;
import Model.City;
import Model.Civilization;
import Model.Tile;
import Model.Worker;
import Model.Unit;
import View.GameMenu;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.asserts.Assertion;

import java.util.ArrayList;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.regex.Matcher;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@ExtendWith(MockitoExtension.class)
public class GameMenuTest {



    static MockedStatic<GameDatabase> gameDatabase;
    GameMenu gameMenu;

    @Mock
    ArrayList<Civilization> civilizations;

    @Mock
    GameMenuController gameMenuController;

    @Mock
    Matcher matcher;

    @Mock
    Tile tile;

    @Mock
    City city;

    @Mock
    Civilization civilization;

    @Mock
    CombatController combatController;

    @Mock
    Worker worker;

    @Mock
    Unit unit;

    @BeforeEach
    public void setUp(){
        gameDatabase = mockStatic(GameDatabase.class);
        gameMenu = new GameMenu(gameMenuController, combatController);
    }

    @Test
    public void buyTileWithCoordinate(){
        int turn = 0;
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        //when(matcher.group("cityName")).thenReturn("tehran");
        //when(Integer.parseInt("1")).thenReturn(1);
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        gameDatabase.when(()->GameDatabase.getCityByXAndY(1,1)).thenReturn(city);
        gameDatabase.when(()->GameDatabase.getCityByName("tehran")).thenReturn(city);
        gameDatabase.when(()->GameDatabase.getCivilizationByTurn(turn)).thenReturn(civilization);
        ////////
        when(gameMenuController.isTileInCivilization(tile,turn)).thenReturn(false);
        when(gameMenuController.isTileInAnyCivilization(tile)).thenReturn(false);
        when(gameMenuController.isTileAdjacentToCivilization(tile, civilization)).thenReturn(true);
        when(civilization.getGold()).thenReturn(100);

        //GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        Assertions.assertEquals("congrats bro you bought it",gameMenu.buyTileWithCoordinate(matcher));
    }

    @Test
    public void changeCapital(){
        int turn = 0;
        when(matcher.group("cityName")).thenReturn("tehran");
        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
        gameDatabase.when(()->GameDatabase.getCityByName("tehran")).thenReturn(city);
        when(gameMenuController.isCityForThisCivilization(turn, city)).thenReturn(true);
        when(gameMenuController.isCityCapital("tehran")).thenReturn(false);
        //GameMenu gameMenu = new GameMenu(gameMenuController,combatController);
        gameDatabase.when(()->GameDatabase.getPlayers()).thenReturn(civilizations);
        when(civilizations.get(turn)).thenReturn(civilization);
        Assertions.assertEquals("capital changed successfully",gameMenu.changeCapital(matcher));
    }

    @Test
    public void unitStopWork(){
        int turn = 0;
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        gameDatabase.when(()->GameDatabase.getTileByXAndY(1,1)).thenReturn(tile);
        gameDatabase.when(()->GameDatabase.getCityByXAndY(1,1)).thenReturn(city);
        when(tile.isRaided()).thenReturn(false);
        when(gameMenuController.isTileInCivilization(tile, turn)).thenReturn(true);
        when(tile.getActiveWorker()).thenReturn(worker);
        when(tile.getIsGettingWorkedOn()).thenReturn(true);
        when(city.getIsGettingWorkedOn()).thenReturn(true);
        Assertions.assertEquals("project stopped successfully",gameMenu.unitStopWork(matcher));
    }
    @Test
    public void mapShowPosition_invalid() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
        assertEquals(gameMenu.mapShowPosition(matcher), "position is not valid");
    }

    @Test
    public void mapShowPosition_valid() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        assertNull(gameMenu.mapShowPosition(matcher));
    }

    @Test
    public void mapShowCity_invalid() {
        when(matcher.group("cityName")).thenReturn("tehran");
        when(gameMenuController.isCityValid("tehran")).thenReturn(false);
        assertEquals(gameMenu.mapShowCity(matcher), "selected city is not valid");
    }

    @Test
    public void mapShowCity_valid() {
        when(matcher.group("cityName")).thenReturn("tehran");
        when(gameMenuController.isCityValid("tehran")).thenReturn(true);
        assertNull(gameMenu.mapShowCity(matcher));
    }

    @Test
    public void selectCombat_invalidPosition() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
        assertEquals(gameMenu.selectCombat(matcher), "position is not valid");
    }

    @Test
    public void selectCombat_nonCombat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(false);
        assertEquals(gameMenu.selectCombat(matcher), "no combat unit");
    }

    @Test
    public void selectCombat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isCombatUnitInThisPosition(1, 1)).thenReturn(true);
        when(gameMenuController.selectCombatUnit(1, 1)).thenReturn(unit);
        assertEquals(gameMenu.selectCombat(matcher), "unit selected");
    }

    @Test
    public void selectNonCombat_invalidPosition() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(false);
        assertEquals(gameMenu.selectNonCombat(matcher), "position is not valid");
    }

    @Test
    public void selectNonCombat_Combat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isNonCombatUnitInThisPosition(1, 1)).thenReturn(false);
        assertEquals(gameMenu.selectNonCombat(matcher), "no noncombat unit");
    }

    @Test
    public void selectNonCombat() {
        when(matcher.group("x")).thenReturn("1");
        when(matcher.group("y")).thenReturn("1");
        when(gameMenuController.isPositionValid(1, 1)).thenReturn(true);
        when(gameMenuController.isNonCombatUnitInThisPosition(1, 1)).thenReturn(true);
        when(gameMenuController.selectNonCombatUnit(1, 1)).thenReturn(unit);
        assertEquals(gameMenu.selectNonCombat(matcher), "unit selected");
    }

    @Test
    public void mapMove_invalidDirection() {
        when(matcher.group("direction")).thenReturn("UP");
        when(matcher.group("c")).thenReturn("1");
        when(gameMenuController.isDirectionForMapValid("UP")).thenReturn(false);
        assertEquals(gameMenu.mapMove(matcher), "invalid direction");
    }

    @Test
    public void mapMove_invalidPosition() {
        when(matcher.group("direction")).thenReturn("UP");
        when(matcher.group("c")).thenReturn("1");
        when(gameMenuController.isDirectionForMapValid("UP")).thenReturn(true);
        when(gameMenuController.getX()).thenReturn(1);
        when(gameMenuController.getY()).thenReturn(1);
        HashMap<String, Integer> directionX = new HashMap<String, Integer>();
        {
            directionX.put("UP", -1);
            directionX.put("DOWN", 1);
            directionX.put("RIGHT", 0);
            directionX.put("LEFT", 0);
        }
        HashMap<String, Integer> directionY = new HashMap<String, Integer>();
        {
            directionY.put("UP", 0);
            directionY.put("DOWN", 0);
            directionY.put("RIGHT", 1);
            directionY.put("LEFT", -1);
        }
        when(gameMenuController.getDirectionX()).thenReturn(directionX);
        when(gameMenuController.getDirectionY()).thenReturn(directionY);
        when(gameMenuController.isPositionValid(0, 1)).thenReturn(false);
        assertEquals(gameMenu.mapMove(matcher), "position is not valid");
    }

    @Test
    public void mapMove() {
        when(matcher.group("direction")).thenReturn("UP");
        when(matcher.group("c")).thenReturn("1");
        when(gameMenuController.isDirectionForMapValid("UP")).thenReturn(true);
        when(gameMenuController.getX()).thenReturn(1);
        when(gameMenuController.getY()).thenReturn(1);
        HashMap<String, Integer> directionX = new HashMap<String, Integer>();
        {
            directionX.put("UP", -1);
            directionX.put("DOWN", 1);
            directionX.put("RIGHT", 0);
            directionX.put("LEFT", 0);
        }
        HashMap<String, Integer> directionY = new HashMap<String, Integer>();
        {
            directionY.put("UP", 0);
            directionY.put("DOWN", 0);
            directionY.put("RIGHT", 1);
            directionY.put("LEFT", -1);
        }
        when(gameMenuController.getDirectionX()).thenReturn(directionX);
        when(gameMenuController.getDirectionY()).thenReturn(directionY);
        when(gameMenuController.isPositionValid(0, 1)).thenReturn(true);
        assertNull(gameMenu.mapMove(matcher));
    }

    @AfterEach
    public void after() {
        gameDatabase.close();
    }
}
