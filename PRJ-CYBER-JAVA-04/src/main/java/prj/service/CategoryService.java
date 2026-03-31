package prj.service;


import prj.dao.CategoryDAO;
import prj.dao.impl.CategoryDaoImpl;

public class CategoryService {
    private CategoryDAO categoryDAO = new CategoryDaoImpl();
}
