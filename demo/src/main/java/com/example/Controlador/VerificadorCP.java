package com.example.Controlador;

public class VerificadorCP {

    public static boolean AllTheSame(String cpf){
        for(int i = 1; i < 11; i++){
            if(cpf.charAt(i) != cpf.charAt(0)){
                return false;
            }
        }

        return true;
    }

    public static String JustDigits(String cpf){
        String numeros = "";

        //remove qualquer caractere não numérico
        for (int i = 0; i < cpf.length(); i++) {
            if (Character.isDigit(cpf.charAt(i))) {
                numeros += cpf.charAt(i);
            }
        }

        return numeros;
    }

    public static boolean VerificationCpf(String cpf){
        String numeros = JustDigits(cpf);

        //verifica se o CPF tem 11 dígitos
        if (numeros.length() != 11) {
            System.out.println("[ERRO] Invalid CPF: It must contain exactly 11 digits.");
            return false;
        }

        //verifica se todos os dígitos são iguais
        if (AllTheSame(numeros)) {
            System.out.println("[ERRO] Invalid CPF: all digits are the same, which is not allowed.");
            return false;
        }

        //validação do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (numeros.charAt(i) - '0') * (10 - i);
        }
        int resultado1 = (soma * 10) % 11;
        if (resultado1 == 10) resultado1 = 0;

        //validação do segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += (numeros.charAt(i) - '0') * (11 - i);
        }
        int resultado2 = (soma2 * 10) % 11;
        if (resultado2 == 10) resultado2 = 0;

        // Verifica se os dígitos batem
        return (resultado1 == (numeros.charAt(9) - '0')) && (resultado2 == (numeros.charAt(10) - '0')); //return false or true
    }

    public static boolean verificationCnpj(String cnpj) {
        String numeros = JustDigits(cnpj);

        if (numeros.length() != 14) {//verifica se o CNPJ tem 14 dígitos
            System.out.println("[ERRO] CNPJ deve conter 14 dígitos.");
            return false;
        }

        if (AllTheSame(numeros)) {//verifica se todos os dígitos são iguais
            System.out.println("[ERRO] CNPJ inválido: dígitos repetidos.");
            return false;
        }

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        //primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += (numeros.charAt(i) - '0') * pesos1[i];
        }
        int resto = soma % 11;
        int dv1 = (resto < 2) ? 0 : 11 - resto;

        //segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 13; i++) {
            soma2 += (numeros.charAt(i) - '0') * pesos2[i];
        }
        int resto2 = soma2 % 11;
        int dv2 = (resto2 < 2) ? 0 : 11 - resto2;

        return dv1 == (numeros.charAt(12) - '0') &&
                dv2 == (numeros.charAt(13)  -   '0');
    }
}
