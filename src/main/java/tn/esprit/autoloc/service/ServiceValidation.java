package tn.esprit.autoloc.service;

import java.math.BigDecimal;

final class ServiceValidation {
    private ServiceValidation() {
    }

    static void requireNewEntity(Object entity, Long id, String resource) {
        if (entity == null) {
            throw new IllegalArgumentException(resource + " obligatoire");
        }
        if (id != null) {
            throw new IllegalArgumentException(
                    "Un nouvel élément " + resource + " ne doit pas avoir d'identifiant");
        }
    }

    static void requireUpdateEntity(Object entity, String resource) {
        if (entity == null) {
            throw new IllegalArgumentException(resource + " obligatoire");
        }
    }

    static void requireId(Long id, String resource) {
        if (id == null) {
            throw new IllegalArgumentException("L'identifiant de " + resource + " est obligatoire");
        }
    }

    static void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }

    static void requireNonNegative(BigDecimal value, String field) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(field + " doit être positif ou nul");
        }
    }
}
