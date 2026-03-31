package prj.dao;

import prj.model.Pc;

import java.util.List;

public interface PcDAO {
    boolean addPc(Pc p);
    List<Pc> getAllPc();
    boolean deletePcsByName(String pcName);
    boolean updatePc(Pc p);
    boolean updateStatus(int id);
    boolean closeStatus(int id);
}
