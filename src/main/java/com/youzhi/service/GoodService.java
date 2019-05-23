package com.youzhi.service;

import com.beust.jcommander.Strings;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.youzhi.model.Goods;

import java.util.List;

public class GoodService {
    private Goods dao = new Goods().dao();

    public Page<Goods> paginate(int pageNumber, int pageSize, String search) {
        if (Strings.isStringEmpty(search)) {
//        if(search == null || search.equals("")){
            return dao.paginate(pageNumber, pageSize, "select *", "from goods order by id asc");
        } else {
            return dao.paginate(pageNumber, pageSize, "select *", "from goods where goodsName like '%" + search + "%'");
        }
//        return dao.paginate(pageNumber,pageSize,"select *","from goods order by id asc");
    }
//    public Page<Goods> paginate(int pageNumber, int pageSize) {
//        return dao.paginate(pageNumber, pageSize, "select *", "from goods order by id asc");
//    }

    public Goods findById(int id) {
        return dao.findById(id);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }

    public  List<Record> getGoodsIdAndNumber() {
        List<Record> IdAndNumberList = Db.find("select id,number from goods");
        return IdAndNumberList;
    }
//    public void updateNumberDatafromRedisToMySQL(String id,String number){
//        Db.update("update goods set number=? where id=?",number,id);
//    }

}
