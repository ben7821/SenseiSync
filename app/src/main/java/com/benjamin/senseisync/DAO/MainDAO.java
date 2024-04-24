package com.benjamin.senseisync.DAO;

public abstract class MainDAO<T>  {
    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     */
    public abstract void insert(T obj);
    /**
     * Permet de mettre à jour les données d'une entrée dans la
     base
     */
    public abstract void update(T obj);
    /**
     * Permet la suppression d'une entrée de la base
     */
    public abstract void delete(T obj);
}
