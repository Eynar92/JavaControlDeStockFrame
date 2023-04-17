package com.alura.controller;

import com.alura.dao.CategoriaDAO;
import com.alura.factory.ConnectionFactory;
import com.alura.modelo.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;

    public CategoriaController() {
        var factory = new ConnectionFactory();
        this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion());
    }

    public List<Categoria> listar() {
        return categoriaDAO.listar();
    }

    public List<Categoria> cargaReporte() {
        return this.categoriaDAO.listarConProductos();
    }

}
