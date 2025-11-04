package com.mycompany.linkkk;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class PelaporIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "PLP";
        Session hibernateSession = (Session) session;

        // Ambil semua ID yang sudah ada
        List<String> existingIds = hibernateSession.createQuery(
                "SELECT p.id_user FROM Pelapor p", String.class).getResultList();

        // Ubah semua ID menjadi angka saja (tanpa huruf)
        List<Integer> numericIds = existingIds.stream()
                .map(id -> {
                    try {
                        return Integer.parseInt(id.replaceAll("[^0-9]", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .sorted()
                .collect(Collectors.toList());

        // Cari angka berikutnya yang belum dipakai
        int nextId = 1;
        for (Integer id : numericIds) {
            if (id == nextId) {
                nextId++;
            } else if (id > nextId) {
                break;
            }
        }

        // Format hasil: PLP00001, PLP00002, dst
        return prefix + new DecimalFormat("00000").format(nextId);
    }
}
