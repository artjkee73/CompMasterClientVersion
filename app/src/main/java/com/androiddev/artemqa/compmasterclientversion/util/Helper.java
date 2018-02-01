package com.androiddev.artemqa.compmasterclientversion.util;

import com.androiddev.artemqa.compmasterclientversion.models.Price;

import java.util.ArrayList;

/**
 * Created by artemqa on 31.01.2018.
 */

public class Helper {
    public static ArrayList<Price> addPriceList(){
        ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price(1,"Ремонт материнской платы","Описание услуги",48,2500));
        priceList.add(new Price(2,"Ремонт видеокарты","Описание услуги",48,3500));
        priceList.add(new Price(3,"Ремонт блока питания","Описание услуги",48,1500));
        priceList.add(new Price(4,"Замена оперативной памяти","Описание услуги",1,500));
        priceList.add(new Price(5,"Чистка ноутбука","Описание услуги",24,1200));
        priceList.add(new Price(6,"Установка ПО","Описание услуги",6,1500));
        priceList.add(new Price(7,"Установка ОС","Описание услуги",6,2500));
        priceList.add(new Price(8,"Установка антивируса","Описание услуги",1,700));
        priceList.add(new Price(9,"Оптимизация системы","Описание услуги",1,900));
        priceList.add(new Price(10,"Сборка компьютера","Описание услуги",24,1000));
    return priceList;
    }
}
