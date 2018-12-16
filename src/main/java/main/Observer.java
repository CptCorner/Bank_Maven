package main;

import verarbeitung.Konto;

public abstract class Observer {
    protected Konto subject;
    public abstract void update();
}