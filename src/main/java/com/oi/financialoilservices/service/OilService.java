package com.oi.financialoilservices.service;

import com.oi.financialoilservices.entity.OilTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OilService {

    public long calculateRevenueYield() {

        return 0;
    }

    public long calculatePriceEarningsRatio() {

        return 0;
    }

    public long calculateGeometricMean() {

        return 0;
    }

    public long calculateVolumeWeightedOilPrice(final List<OilTransaction> transactons, final long price) {

        // long totalQuantity =  transactons.stream().mapToLong(o -> o.getVolume()).sum();

        long totalQuantity = 0;
        long totalQuantityXPrice = 0;

        try {
            for (OilTransaction transaction : transactons) {
                totalQuantity += transaction.getVolume();
                totalQuantityXPrice += transaction.getVolume() * price;
            }
            return totalQuantityXPrice / totalQuantity;
        } catch (Exception exception) {
            return 0;
        }
    }
}
