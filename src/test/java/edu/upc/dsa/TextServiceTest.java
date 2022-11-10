package edu.upc.dsa;

import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextServiceTest {
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    GameManager manager;

    @Before
    public void setUp() {
        manager = new GameManagerImpl();

        manager.createJuego("joc1","descr", 5);

    }

    @After
    public void tearDown()  {
        this.manager = null;
        logger.info("--- End of the test ---");
    }

    @Test
    public void addGame() {
        logger.info("--- Start of the test -  ---");

        Assert.assertEquals(1,this.manager.sizeGames());

        logger.info("Condicions inicials: ");
        /*


        logger.info("S'afegeix 1 usuari: Paula");
        this.manager.addUser(new VOuser("444","Paula","Zuckerberg", "23/05/2001", "paint@love.com",  "pandas"));

        Assert.assertEquals(4,this.manager.sizeUsers());
         */
    }
    @Test
    public void logIn() {
        logger.info("--- Start of the test - LogIn ---");
    }

    @Test
    public void sort(){
        logger.info("--- Start of the test - Sort Objectes ---");
        /*
        List<Objecte> initialobjectes = this.manager.getAllObjectes();

        Assert.assertEquals(50, initialobjectes.get(0).getDsaCoins());
        Assert.assertEquals(2, initialobjectes.get(1).getDsaCoins());
        Assert.assertEquals(149, initialobjectes.get(2).getDsaCoins());
        Assert.assertEquals(9, initialobjectes.get(3).getDsaCoins());

        List<Objecte> sortedobjectes = this.manager.sortNumObjectes();

        Assert.assertEquals(149, sortedobjectes.get(0).getDsaCoins());
        Assert.assertEquals(50, sortedobjectes.get(1).getDsaCoins());
        Assert.assertEquals(9, sortedobjectes.get(2).getDsaCoins());
        Assert.assertEquals(2, sortedobjectes.get(3).getDsaCoins());
         */
    }
}
