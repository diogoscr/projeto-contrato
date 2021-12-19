package application;

import entities.*;
import java.util.*;
import java.text.*;
import java.io.IOException;
import java.time.Instant;

public class AppContrato {

    static List<Colaborador> colaboradores = new ArrayList<>();
    static List<Contrato> contratos = new ArrayList<>();
    static List<VendaComissionada> vendas = new ArrayList<>();
    static DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    static DateFormat outdf = new SimpleDateFormat("dd/MM/yyyy");
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            int opcao;
            do {
                System.out.println("\n*** Seletor de Opções ***\n");
                System.out.println("1 - Inserir colaborador");
                System.out.println("2 - Registrar contrato");
                System.out.println("3 - Consultar contrato");
                System.out.println("4 - Encerrar contrato");
                System.out.println("5 - Listar colaboradores ativos");
                System.out.println("6 - Consultar contratos do colaborador");
                System.out.println("7 - Lançar vendas comissionadas");
                System.out.println("8 - Emitir folha de pagamento");
                System.out.println("0 - Finalizar");

                System.out.print("\nOpção: ");
                opcao = sc.nextInt();

                switch (opcao) {
                    case 1 -> inserirColaborador();
                    case 2 -> registrarContrato();
                    case 3 -> consultarContrato();
                    case 4 -> encerrarContrato();
                    case 5 -> listarColaboradoresAtivos();
                    case 6 -> consultarContratosColaborador();
                    case 7 -> lancarVendasComissionadas();
                    case 8 -> emitirFolhaPagamento();
                    case 0 -> {
                        System.out.println("\n--- PROGRAMA FINALIZADO ---");
                        clearScreen();
                        sc.close();
                        return;
                    }
                    default -> System.out.println("\n--- Opção incorreta ---");
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException e) { e.printStackTrace(); }
    }

    public static Colaborador pesquisarColaborador(int mat) {
        for (Colaborador colab : colaboradores) {
            if (colab.getMatricula() == mat) {
                return colab;
            }
        }
        return null;
    }

    public static Contrato pesquisarContrato(int cod) {
        for (Contrato contrato : contratos) {
            if (contrato.getId() == cod) {
                return contrato;
            }
        }
        return null;
    }

    public static String tipoContrato(Contrato contrato) {
            if (contrato instanceof ContratoAssalariado) {
                return "entities.Contrato Assalariado";
            }
            if (contrato instanceof ContratoComissionado){
                return "entities.Contrato Comissionado";
            }
            if (contrato instanceof ContratoHorista){
            return "entities.Contrato Horista";
            }
        return null;
    }

    public static void inserirColaborador() throws ParseException {
        System.out.println("\n*** Inserir entities.Colaborador ***");
        while (true) {
            System.out.print("\nDigite a matrícula do colaborador ou [0] para voltar ao menu principal: ");
            int matricula;
            try {
                matricula = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("--- Matrícula deve ser apenas numérica ---");
                sc.next();
                break;
            }
            if (matricula == 0) {
                return;
            }

            if (pesquisarColaborador(matricula) != null) {
                System.out.println("\n--- Matrícula já cadastrada ---");
                continue;
            }

            String CPF;
            boolean cpfOk;
            sc.nextLine();
            do {
                cpfOk = true;
                System.out.print("\nDigite o CPF do colaborador: ");
                CPF = sc.nextLine();

                if (!Colaborador.validarCpf(CPF)) {
                    System.out.println("\n--- CPF inválido ---");
                    cpfOk = false;
                }else {
                    for (Colaborador c : colaboradores) {
                        if (CPF.equals(c.getCpf())) {
                            System.out.println("\n--- CPF já foi registrado ---");
                            cpfOk = false;
                        }
                    }
                }
            }while (!cpfOk);

            System.out.print("\nNome do colaborador : ");
            String nome = sc.nextLine();

            Date dataFormatada;
            do{
                System.out.print("\nData de nascimento : ");
                dataFormatada = sdf.parse(sc.next());

                if (calcularIdade(dataFormatada)<18) {
                    System.out.println("\nData não pode ser menor que 18 anos");
                }
            } while (calcularIdade(dataFormatada)<18);

            Colaborador colaborador = new Colaborador(matricula, CPF, nome, dataFormatada);
            colaboradores.add(colaborador);
            System.out.println("\n*** entities.Colaborador inserido com sucesso ***");
        }
    }

    public static int calcularIdade(Date dataNascimento) {

        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNascimento);
        Calendar today = Calendar.getInstance();

        int idade = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        if (today.before(dateOfBirth)) {
            idade--;
        }
        return idade;
    }

    public static void registrarContrato() throws ParseException {
        System.out.println("\n*** Registrar entities.Contrato ***");
        do {
            System.out.print("\nDigite a matrícula do coladorador ou (0) para voltar ao menu principal: ");

            int mat = sc.nextInt();
            if (mat == 0) {
                return;
            }

            Colaborador colab = pesquisarColaborador(mat);

            if (colab == null) {
                System.out.println("\n--- entities.Colaborador não encontrado, tente novamente ---");
                continue;
            }

            Date dataInicio;
            Date dataAtual;
            do {
                System.out.print("\nData de início do entities.Contrato? ");
                dataInicio = sdf.parse(sc.next());

                dataAtual = Date.from(Instant.now());

                if (dataInicio.before(dataAtual)) {
                    System.out.println("\n--- Data de início não pode ser anterior à data atual ---");
                }
            } while (dataInicio.before(dataAtual));

            int opcao;
            do {
                System.out.println("\n*** Selecione o tipo de entities.Contrato ***\n");
                System.out.println("1 - entities.Contrato assalariado");
                System.out.println("2 - entities.Contrato comisissionado");
                System.out.println("3 - entities.Contrato horista");

                opcao = sc.nextInt();

                double salarioMensal = 0.0;
                double pericInsalubridade = 0.0;
                double pericPericulosidade = 0.0;
                double pericComissao = 0.0;
                double ajudaCusto = 0.0;
                int horasMes = 0;
                double valorHora = 0.0;
                if (opcao == 1) {
                    System.out.println("\n--- entities.Contrato assalariado ---");
                    System.out.println("\n*** Insira o valor do salário ***");
                    salarioMensal = sc.nextDouble();
                    System.out.println("\n*** Percentual de insalubridade ***");
                    pericInsalubridade = sc.nextDouble();
                    System.out.println("\n*** Percentual de periculosidade ***");
                    pericPericulosidade = sc.nextDouble();

                } else if (opcao == 2) {
                    System.out.println("\n--- entities.Contrato comissionado ---");
                    System.out.println("\n*** Percentual de comissão ***");
                    pericComissao = sc.nextDouble();
                    System.out.println("\n*** Ajuda de custo ***");
                    ajudaCusto = sc.nextDouble();

                } else if (opcao == 3) {
                    System.out.println("\n--- entities.Contrato horista ---");
                    System.out.println("\n*** Quantidade de horas mensais ***");
                    horasMes = sc.nextInt();
                    System.out.println("\n*** Valor da hora de trabalho ***");
                    valorHora = sc.nextDouble();

                } else {
                    System.out.println("--- Tipo não existe, escolha novamente ---");
                    continue;
                }

                char confirm;
                Contrato contrato;
                sc.nextLine();
                do {
                    System.out.println("\n*** Deseja confirmar registro - [S] sim , [N] não ***");
                    confirm = sc.next().toUpperCase().charAt(0);

                    if (confirm != 'S' && confirm != 'N') {
                        System.out.println("\nOpção não reconhecida - digite 'S' para sim ou 'N' para não");
                        continue;
                    }
                    if (confirm == 'N') {
                        break;
                    }
                    else if (opcao ==1) {
                        contrato = new ContratoAssalariado(dataInicio, colab, salarioMensal, pericInsalubridade, pericPericulosidade);
                        contratos.add(contrato);
                    }
                    else if (opcao==2) {
                        contrato = new ContratoComissionado(dataInicio,colab,pericComissao,ajudaCusto);
                        contratos.add(contrato);
                    }
                    else {
                        contrato = new ContratoHorista(dataInicio,colab,horasMes,valorHora);
                        contratos.add(contrato);
                    }
                    colab.ativar();
                    System.out.println("\n*** entities.Contrato num. "+contrato.getId()+" efetivado com sucesso! ***");
                } while (confirm != 'S');

            } while (opcao!=1 && opcao!=2 && opcao !=3);

        } while (true);
    }

    public static void consultarContrato() {
        System.out.println("\n*** Consultar entities.Contrato ***");
        while (true) {
            System.out.println("\n*** Inserir código do contrato ou (0) para voltar ao menu principal ***");
            int codigo = sc.nextInt();
            if (codigo == 0) {
                break;
            }
            Contrato contrato = pesquisarContrato(codigo);
            if (contrato == null) {
                System.out.println("\n--- entities.Contrato não cadastrado---");
            } else {
                String data = outdf.format(contrato.getDataInicio());
                String dt = outdf.format(contrato.getDataEncerramento());
                System.out.println("entities.Contrato num: " + contrato.getId() +
                        "\nData de início........: " + data);
                if (dt.equals("30/11/0002")) System.out.println("Data de encerramento..: -" );
                else System.out.println("Data de encerramento..: "+dt);
                System.out.println("Situação.......: " + contrato.isAtivo() +
                        "\nTipo do entities.Contrato: " + tipoContrato(contrato) +
                        "\nMatrícula do colaborador: " + contrato.getColaborador().getMatricula() +
                        "\nCPF............: " + contrato.getColaborador().getCpf() +
                        "\nNome...........: " + contrato.getColaborador().getNome() +
                        "\nSituação.......: " + contrato.getColaborador().isSituacao());
            }
        }
    }

    public static void encerrarContrato() throws ParseException {
        System.out.println("\n*** Encerrar entities.Contrato ***");
        while (true) {
            System.out.println("\n*** Inserir código do entities.Contrato ou (0) para voltar ao menu principal ***");
            int codigo = sc.nextInt();
            if (codigo == 0) {
                break;
            }
            Contrato contrato = pesquisarContrato(codigo);
            if (contrato == null) {
                System.out.println("\n--- entities.Contrato não cadastrado---");
            } else if (!contrato.isAtivo()) {
                    System.out.println("\n--- entities.Contrato já encerrado---");
            } else {
                Date dataEncerramento;
                Date dataAtual;
                do {
                    System.out.println("\n*** Data de encerramento ***");
                    dataEncerramento = sdf.parse(sc.next());
                    dataAtual = Date.from(Instant.now());
                    if (dataEncerramento.after(dataAtual)) {
                        System.out.println("\nData de encerramento não pode ser posterior à data atual");
                    }
                } while (dataEncerramento.after(dataAtual));
                char confirm;
                do {
                    System.out.println("\n*** Deseja confirmar registro - [S] sim , [N] não ***");
                    confirm = sc.next().toUpperCase().charAt(0);

                    if (confirm != 'S' && confirm != 'N') {
                        System.out.println("Opção inválida - digite 'S'- para sim ou 'N' - para não");
                        continue;
                    }
                    if (confirm == 'N') {
                        break;
                    }
                    else if (dataEncerramento.before(contrato.getDataInicio())) {
                        System.out.println("--- Data de encerramento não pode ser anterior à data de início ---");
                    } else {
                        contrato.encerrarContrato(dataEncerramento);
                        System.out.println("\n*** entities.Contrato encerrado com sucesso! ***");
                        hasContrato(contrato.getColaborador());
                    }
                } while (confirm != 'S');
            }
        }
    }

    public static void hasContrato(Colaborador colab) {
        for (Contrato contrato : contratos) {
            if (contrato.getColaborador() != null) colab.ativar();
        }
        colab.desativar();
    }

    public static void listarColaboradoresAtivos() {
        System.out.println("\n*** Listar Colaboradores Ativos ***");
        for (Colaborador colab : colaboradores) {
            if (colab.isSituacao()) {
                System.out.println(
                        "\nMatrícula do colaborador: " + colab.getMatricula() +
                        "\nCPF............: " + colab.getCpf() +
                        "\nNome...........: " + colab.getNome());
            }
        }
        if (colaboradores.isEmpty()) System.out.println("\n--- Não existem colaboradores ativos ---");
    }

    public static void consultarContratosColaborador() {
        System.out.println("\n*** Consultar Contratos do entities.Colaborador ***");
        while (true) {
            System.out.println("\n*** Inserir a matrícula ou CPF do colaborador ou (0) para voltar ao menu principal ***\n");
            int identificador = sc.nextInt();

            if (identificador == 0) {
                break;
            }

            for (Contrato contrato : contratos) {
                if (contrato.getColaborador().getMatricula() == identificador || contrato.getColaborador().getCpf().equals(Integer.toString(identificador))) {
                    System.out.println("Matrícula do colaborador:" + contrato.getColaborador().getMatricula() +
                            "\n CPF.................:" + contrato.getColaborador().getCpf() +
                            "\n Nome................:"+ contrato.getColaborador().getNome() +
                            "\n Situação............:" + contrato.getColaborador().isSituacao() +
                            "\n Id do entities.Contrato......:" + contrato.getId() +
                            "\n Tipo do contrto.....:" + tipoContrato(contrato) +
                            "\n Situação do entities.Contrato:" + contrato.isAtivo());
                }
            }
        }
    }

    public static void lancarVendasComissionadas() {
        System.out.println("\n*** Lançar Vendas Comissionadas ***");
        while (true) {
            System.out.println("\n*** Inserir identificador do entities.Contrato ou (0) para voltar ao menu principal***\n");
            int identificador = sc.nextInt();

            if (identificador == 0) {
                break;
            }
            Contrato contrato = pesquisarContrato(identificador);
            if (contrato == null) {
                System.out.println("\n--- entities.Contrato não cadastrado---\n");
            } else if (!contrato.isAtivo()) {
                System.out.println("\n--- entities.Contrato já encerrado---\n");
            } else if (!tipoContrato(contrato).equals("entities.Contrato Comissionado")){
                System.out.println("entities.Contrato não é do tipo comissionado");
            }
            else {
                System.out.println("Digite o mês das vendas");
                int mes = sc.nextInt();
                System.out.println("Digite o ano das vendas");
                int ano = sc.nextInt();
                System.out.println("Digite o valor das vendas");
                double valor = sc.nextDouble();
                VendaComissionada venda = new VendaComissionada(mes,ano,valor, (ContratoComissionado) contrato);
                vendas.add(venda);
                System.out.println("\nVenda lançada com sucesso!");
            }
        }
    }

    public static void emitirFolhaPagamento() {
        System.out.println("\n*** Emitir Folha de Pagamento ***");
        while (true) {
            System.out.println("\n*** Inserir ano ou (0) para voltar ao menu principal***");
            int ano = sc.nextInt();
            if (ano == 0) {
                break;
            }
            else if (ano <0) {
                System.out.println("\nAno inválido, digite novamente");
                continue;
            }
            int mes;
            do {
                System.out.println("\nDigite o mês das vendas");
                mes = sc.nextInt();
                if (mes<1 || mes>12) {
                    System.out.println("\nMês inválido, digite novamente");
                }
            }while (mes<1 || mes>12);
            double salario = 0.0;
            for (Contrato contrato : contratos){
                if (contrato.isAtivo()) {
                    String tipo = tipoContrato(contrato);
                    if ( tipo.indexOf("Assalariado") > 0 ) {
                        assert contrato instanceof ContratoAssalariado;
                        ContratoAssalariado contratoAssal = (ContratoAssalariado) contrato;
                        salario = contratoAssal.calcVencimento();
                    }
                    if ( tipo.indexOf("Comissionado") > 0 ) {
                        assert contrato instanceof ContratoComissionado;
                        ContratoComissionado contratoComiss = (ContratoComissionado) contrato;
                        double valor = 0;
                        for (VendaComissionada venda : vendas) {
                            if (venda.getAno() == ano && venda.getMes() == mes && venda.getContratoComissionado() == contrato) {
                                valor += venda.getValor();
                            }
                        }
                        salario = contratoComiss.calcVencimento(valor);
                    }
                    if ( tipo.indexOf("Horista") > 0 ) {
                        assert contrato instanceof ContratoHorista;
                        ContratoHorista contratoHor = (ContratoHorista) contrato;
                        salario = contratoHor.calcVencimento();
                    }

                    contrato.setUltimoVencimento(salario);

                    System.out.println(
                            "\nId do entities.Contrato..........: " + contrato.getId() +
                            "\nTipo do entities.Contrato........: " + tipo +
                            "\nSituação do entities.Contrato....: " + contrato.isAtivo() +
                            "\nMatrícula do entities.Colaborador: " + contrato.getColaborador().getMatricula() +
                            "\nNome....................: "+ contrato.getColaborador().getNome());
                    System.out.printf("Salário.................: %.2f", salario);
                }
            }
        }
    }
}