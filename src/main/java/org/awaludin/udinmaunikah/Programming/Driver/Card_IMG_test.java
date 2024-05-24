package org.awaludin.udinmaunikah.Programming.Driver;

import org.awaludin.udinmaunikah.Programming.*;

public class Card_IMG_test{
    public static void main(String[] args) {
        GameObjectFactory.Load();
        GameObject test = GameObjectFactory.CreateGameObjectByID("PLANT_002");
        Card kartu = new Card(test);
        System.out.println(kartu.getImagePath());

        GameObject plt = kartu.convertToGameObject();
        Plant F = (Plant) plt;

        System.out.println(F.GetAge());

    }
}
