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

        manager.createJuego("pichi","com el beisball", 5);
        manager.createJuego("so-pa-po","el matias sempre guanya", 2);
        manager.iniciarPartida("so-pa-po", "alex");
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

        logger.info("Afegim un joc:");
        manager.createJuego("mata conills","matar", 10);

        Assert.assertEquals(2,this.manager.sizeGames());
        /*
        logger.info("S'afegeix 1 usuari: Paula");
        this.manager.addUser(new VOuser("444","Paula","Zuckerberg", "23/05/2001", "paint@love.com",  "pandas"));

        Assert.assertEquals(4,this.manager.sizeUsers());
         */
    }
    @Test
    public void inicioPartida() {
        logger.info("--- Start of the test - Iniciar partida ---");
        manager.iniciarPartida("so-pa-po", "matias");

        logger.info("Matias intenta de iniciar otra partida mientras ya esta jugando a una:");
        manager.iniciarPartida("pichi", "matias");

        logger.info("Matias quiere saber en que nivel esta:");
        manager.getNivelActual("matias");

        logger.info("Matias quiere saber cuantos puntos tiene:");
        manager.getPuntuacionActual("matias");

        logger.info("Vamos a mirar en que partida esta matias:");
        manager.getPartida("so-pa-po", "matias");
        manager.getPartida("pichi", "matias");

        logger.info("Vamos a mirar en que partida esta matias:");
        manager.getPartidaActual("matias");

        manager.endPartida("matias");

        logger.info("Vamos a volver a mirar en que partida esta matias:");
        manager.getPartidaActual("matias");

        logger.info("Matias quiere volver a saber en que nivel esta:");
        manager.getNivelActual("matias");

        logger.info("Matias quiere volver a saber cuantos puntos tiene:");
        manager.getPuntuacionActual("matias");


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
