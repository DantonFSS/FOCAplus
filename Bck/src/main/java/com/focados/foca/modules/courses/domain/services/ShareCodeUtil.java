package com.focados.foca.modules.courses.domain.services;

import java.util.function.Predicate;

public class ShareCodeUtil {
    // Recebe prefixo, nome/título e predicate pra checar se existe
    public static String generateShareCode(String prefix, String base, Predicate<String> codeExists) {
        String codePrefix = (prefix + "-" + extractInitials(base)).toUpperCase();
        String randomSuffix;
        String shareCode;
        do {
            randomSuffix = java.util.UUID.randomUUID().toString()
                    .replaceAll("-", "")
                    .substring(0, 6)
                    .toUpperCase();
            shareCode = codePrefix + "-" + randomSuffix;
        } while (codeExists.test(shareCode));
        return shareCode;
    }

    private static String extractInitials(String str) {
        return str.replaceAll("[^A-Za-z]", " ")
                .replaceAll("\\s+", " ")
                .trim()
                .replaceAll("([A-Za-z])[A-Za-z]* ?", "$1")
                .toUpperCase();
    }
}