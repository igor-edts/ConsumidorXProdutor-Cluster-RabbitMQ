package com.example.common.model;

public enum ProductType {
    TYPE_1(3000), // 3 segundos para produção
    TYPE_2(5000); // 5 segundos para produção

    private final long productionTime;

    ProductType(long productionTime) {
        this.productionTime = productionTime;
    }

    public long getProductionTime() {
        return productionTime;
    }

    public long getConsumptionTime() {
        return productionTime * 2; // Tempo de consumo é o dobro do tempo de produção
    }
}