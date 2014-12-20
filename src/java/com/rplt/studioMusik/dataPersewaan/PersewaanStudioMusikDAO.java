/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rplt.studioMusik.dataPersewaan;

import com.rplt.studioMusik.controller.MemberController;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agustinus Agri
 */
@Repository
public class PersewaanStudioMusikDAO implements IPersewaanStudioMusikDAO<PersewaanStudioMusik> {

    @Autowired
    private DataSource dataSource;

    @Override
    public void simpanData(PersewaanStudioMusik pPersewaanStudioMusik) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
//        String kode = getGeneratedKodeSewa();


        String sql = "insert into PERSEWAAN_STUDIO_MUSIK "
                + "values(?, ?, ?, ?, to_date(?, 'dd-MON-yyyy HH24:MI'), to_date(?, 'dd-MON-yyyy HH24:MI'), ?)";

        final String sqlmysql = "INSERT INTO `studiomusik`.`persewaan_studio_musik` "
                + "(`KODE_STUDIO`, `NAMA_PENYEWA`, `NOMOR_TELEPON`, `MULAI_SEWA`, `SELESAI_SEWA`, `BIAYA_PELUNASAN`) "
                + "VALUES (?, ?, ?, STR_TO_DATE(?, '%d-%b-%Y %k:%S'), "
                + "STR_TO_DATE(?, '%d-%b-%Y %k:%S'), ?)";

        System.out.println(sql);

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection cnctn) throws SQLException {
                PreparedStatement ps = cnctn.prepareStatement(sqlmysql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, pPersewaanStudioMusik.getmKodeStudio());
                ps.setString(2, pPersewaanStudioMusik.getmNamaPenyewa());
                ps.setString(3, pPersewaanStudioMusik.getmNomorTeleponPenyewa());
                ps.setString(4, pPersewaanStudioMusik.getmMulaiSewa());
                ps.setString(5, pPersewaanStudioMusik.getmSelesaiSewa());
                ps.setInt(6, pPersewaanStudioMusik.getmBiayaPelunasan());
                return ps;
            }
        }, keyHolder);

//        jdbcTemplate.update(sql,
//                new Object[]{
//                    kode,
//                    pPersewaanStudioMusik.getmKodeStudio(),
//                    pPersewaanStudioMusik.getmNamaPenyewa(),
//                    pPersewaanStudioMusik.getmNomorTeleponPenyewa(),
//                    pPersewaanStudioMusik.getmMulaiSewa(),
//                    pPersewaanStudioMusik.getmSelesaiSewa(),
//                    pPersewaanStudioMusik.getmBiayaPelunasan()
//                });
        System.out.println(pPersewaanStudioMusik.getmMulaiSewa() + "sd" + pPersewaanStudioMusik.getmSelesaiSewa());

        pPersewaanStudioMusik.setmKodeSewa(keyHolder.getKey().toString());
        
        System.out.println("KEYHOLDER : "+pPersewaanStudioMusik.getmKodeSewa());

        System.out.println(pPersewaanStudioMusik.getmKodeSewa());

    }

    @Override
    public boolean cekKetersediaanJadwal(PersewaanStudioMusik pPersewaanStudioMusik) {
        List<PersewaanStudioMusik> pegawaiList = new ArrayList<PersewaanStudioMusik>();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "SELECT to_char((to_date(?, 'HH24:MI') + ? / 24), 'HH24:MI') FROM dual";

//        sql = "SELECT date_format(date_add((STR_TO_DATE(?, '%d-%b-%Y %k:%S')), INTERVAL ? HOUR), '%d-%b-%Y %k:%S')";
//
//        String selesaiSewa = jdbcTemplate.queryForObject(sql, 
//                new Object[]{
//                    pPersewaanStudioMusik.getmMulaiSewa(), 
//                    pPersewaanStudioMusik.getmDurasi()}, 
//                String.class);
//
//        System.out.println(selesaiSewa);
//        
//        pPersewaanStudioMusik.setmSelesaiSewa(selesaiSewa);
        sql = "SELECT * FROM PERSEWAAN_STUDIO_MUSIK WHERE KODE_STUDIO = ? "
                + "AND to_char(MULAI_SEWA, 'dd-MON-yyyy') = ? "
                + "AND to_char(mulai_sewa,'HH24:MI') BETWEEN ? AND ? "
                + "AND "
                + "(to_char(SELESAI_SEWA,'HH24:MI') > ? AND "
                + "to_char(MULAI_SEWA,'HH24:MI') < ?)";

        sql = "SELECT * FROM `studiomusik`.`persewaan_studio_musik` "
                + "WHERE KODE_STUDIO = ? "
                + "AND STR_TO_DATE(?, '%d-%b-%Y %k:%S') < `SELESAI_SEWA` "
                + "AND STR_TO_DATE(?, '%d-%b-%Y %k:%S') > `MULAI_SEWA`";

//        System.out.println(pPersewaanStudioMusik.getmMulaiSewa() + " " + jamMulai + "sampai" + selesaiSewa);
        pegawaiList = jdbcTemplate.query(sql,
                new Object[]{
                    pPersewaanStudioMusik.getmKodeStudio(),
                    pPersewaanStudioMusik.getmMulaiSewa(),
                    pPersewaanStudioMusik.getmSelesaiSewa()
                },
                new PersewaanStudioMusikRowMapper());

        System.out.println("list size : " + pegawaiList.size());

        if (pegawaiList.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public int hitungBiayaSewa(int pDurasi, String pKodeStudio) {
        String sql = "SELECT (? * tarif_per_jam) FROM studio_musik WHERE kode_studio = ?";

        sql = "SELECT (? * `TARIF_PER_JAM`) FROM `studiomusik`.`studio_musik` WHERE `KODE_STUDIO` = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(sql, new Object[]{pDurasi, pKodeStudio}, Integer.class);
    }

    @Override
    public String getGeneratedKodeSewa() {
        String sql = "SELECT to_char(max(kode_sewa) + 1, 'FM099999') FROM persewaan_studio_musik";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = jdbcTemplate.queryForObject(sql, String.class);
        if (query == null) {
            return "100001";
        } else {
            return query;
        }
    }

    @Override
    public List<PersewaanStudioMusik> getDataListByMonth(String pDate) {

        String sql = "select KODE_SEWA, KODE_STUDIO, NAMA_PENYEWA, NOMOR_TELEPON, to_char(MULAI_SEWA, 'dd-MON-yyyy HH24:MI'), to_char(SELESAI_SEWA, 'dd-MON-yyyy HH24:MI'), BIAYA_PELUNASAN "
                + "from PERSEWAAN_STUDIO_MUSIK "
                + "WHERE to_char(mulai_sewa, 'dd-MON-yyyy') = ? ORDER BY mulai_sewa ASC";

        sql = "select `KODE_SEWA`, `KODE_STUDIO`, `NAMA_PENYEWA`, `NOMOR_TELEPON`, "
                + "date_format(`MULAI_SEWA`, '%d-%b-%Y %k:%S'), date_format(`SELESAI_SEWA`, '%d-%b-%Y %k:%S'), `BIAYA_PELUNASAN` "
                + "from `studiomusik`.`persewaan_studio_musik` WHERE date_format(mulai_sewa, '%d-%b-%Y') = ? ORDER BY mulai_sewa ASC";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(sql, new Object[]{pDate}, new PersewaanStudioMusikRowMapper());
    }

    @Override
    public String selesaiSewa(PersewaanStudioMusik pPersewaanStudioMusik) {
        String sql = "SELECT date_format(date_add((STR_TO_DATE(?, '%d-%b-%Y %k:%S')), INTERVAL ? HOUR), '%d-%b-%Y %k:%S')";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String selesaiSewa = jdbcTemplate.queryForObject(sql,
                new Object[]{
                    pPersewaanStudioMusik.getmMulaiSewa(),
                    pPersewaanStudioMusik.getmDurasi()},
                String.class);
        return selesaiSewa;
    }

    @Override
    public byte[] cetakNota(String pKodeSewa, File pReportPath) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("P_KODESEWA", pKodeSewa);
        byte[] bytes = null;
        
        try {
            bytes = JasperRunManager.runReportToPdf(pReportPath.getPath(), params, dataSource.getConnection());
        } catch (JRException ex) {
            Logger.getLogger(MemberController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersewaanStudioMusikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bytes;
    }

    

    public static class PersewaanStudioMusikRowMapper implements RowMapper<PersewaanStudioMusik> {

        @Override
        public PersewaanStudioMusik mapRow(ResultSet rs, int i) throws SQLException {
            PersewaanStudioMusikExtractor persewaanStudioMusikExtractor = new PersewaanStudioMusikExtractor();
            return persewaanStudioMusikExtractor.extractData(rs);
        }
    }

    public static class PersewaanStudioMusikExtractor implements ResultSetExtractor<PersewaanStudioMusik> {

        @Override
        public PersewaanStudioMusik extractData(ResultSet rs) throws SQLException, DataAccessException {
            PersewaanStudioMusik persewaanStudioMusik = new PersewaanStudioMusik();

            persewaanStudioMusik.setmKodeSewa(rs.getString(1));
            persewaanStudioMusik.setmKodeStudio(rs.getString(2));
            persewaanStudioMusik.setmNamaPenyewa(rs.getString(3));
            persewaanStudioMusik.setmNomorTeleponPenyewa(rs.getString(4));
            persewaanStudioMusik.setmMulaiSewa(rs.getString(5));
            persewaanStudioMusik.setmSelesaiSewa(rs.getString(6));
            persewaanStudioMusik.setmBiayaPelunasan(Integer.parseInt(rs.getString(7)));

            return persewaanStudioMusik;
        }

    }

}
