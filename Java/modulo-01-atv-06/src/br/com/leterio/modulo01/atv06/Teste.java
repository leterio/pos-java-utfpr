package br.com.leterio.modulo01.atv06;

import java.util.List;

public class Teste {
	private static final Leitura leitura = new Leitura(); /* neste contexto/arquitetura, deveria ser estática (helper/util) */

	private static final BDVeiculos bdVeiculos = new BDVeiculos();

	public static void main(String[] args) {
		menu();
	}

	// Menus

	private static void menu() {
		while (true) {
			out("\nSistema de Gestão de Veículos - Menu Inicial");
			out("1. Cadastrar Veículo de Passeio");
			out("2. Cadastrar Veículo de Carga");
			out("3. Imprimir Todos os Veículos de Passeio");
			out("4. Imprimir Todos os Veículos de Carga");
			out("5. Imprimir Veículo de Passeio pela Placa");
			out("6. Imprimir Veículo de Carga pela Placa");
			out("7. Sair do Sistema");

			try {
				switch ((int) leitura.entDadosNumericosObrigatorios("> Digite a sua opção:") /* Apenas a parte inteira */) {
					case 1:
						menuInsertVeiculo(Passeio.class);
						break;
					case 2:
						menuInsertVeiculo(Carga.class);
						break;
					case 3:
						findAndPrintVeiculosOf(Passeio.class);
						break;
					case 4:
						findAndPrintVeiculosOf(Carga.class);
						break;
					case 5:
						findAndPrintVeiculosByPlaca(Passeio.class);
						break;
					case 6:
						findAndPrintVeiculosByPlaca(Carga.class);
						break;
					case 7:
						out("Obrigado e até logo ;D");
						return;
					default:
						err("Você selecionou uma opção inválida.");
				}
			} catch (VeicExistException | BDVeiculoCapacityException e) {
				err(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	private static void menuInsertVeiculo(Class<? extends Veiculo> typeOf) throws VeicExistException, BDVeiculoCapacityException {
		do {
			Veiculo veiculo = null;
			
			if (Passeio.class.equals(typeOf)) {
				veiculo = new Passeio();
			} else if (Carga.class.equals(typeOf)) {
				veiculo = new Carga();
			}
			
			insertVeiculo(veiculo);

			menuInsertVeiculoOnLoop: while (true) {
				out("\nDeseja inserir outro veículo?");
				out("1. Sim");
				out("2. Não");

				switch ((int) leitura.entDadosNumericosObrigatorios("> Digite a sua opção:")) {
					case 1:
						break menuInsertVeiculoOnLoop;
					case 2:
						return;
					default:
						err("Você selecionou uma opção inválida.");
				}
			}
		} while (true);
	}

	// Cadastro do veículo

	private static <T extends Veiculo> void insertVeiculo(T veiculo) throws VeicExistException, BDVeiculoCapacityException {
		if (veiculo instanceof Passeio) {
			bdVeiculos.canAddPasseio();
		} else if (veiculo instanceof Carga) {
			bdVeiculos.canAddCarga();
		}

		readDataFor(veiculo);

		if (veiculo instanceof Passeio) {
			bdVeiculos.addPasseio((Passeio) veiculo);
		} else if (veiculo instanceof Carga) {
			bdVeiculos.addCarga((Carga) veiculo);
		}

		out("Veículo cadastrado");
		formatAndPrintVeiculo(veiculo);
	}

	private static <T extends Veiculo> void readDataFor(T veiculo) {
		out("\nDigite os dados do novo veículo:");

		if (veiculo instanceof Passeio) {
			populatePasseioEpecializationData((Passeio) veiculo);
		} else if (veiculo instanceof Carga) {
			populateCargaEpecializationData((Carga) veiculo);
		}

		veiculo.setPlaca(leitura.entDadosObrigatorios("Digite a placa do veículo:"));
		veiculo.setMarca(leitura.entDadosObrigatorios("Digite a marca do veículo:"));
		veiculo.setModelo(leitura.entDadosObrigatorios("Digite o modelo do veículo:"));

		float velocMax = (float) leitura.entDadosNumericosObrigatorios("Digite a velocidade máxima do veículo (KM/h):");
		try {
			validateVelocMax(velocMax);
		} catch (VelocException e) {
			err(e.getMessage());
			if (veiculo instanceof Passeio) {
				veiculo.setVelocMax(100);
			} else if (veiculo instanceof Carga) {
				veiculo.setVelocMax(90);
			}
		}

		veiculo.setMotor(new Motor());
		veiculo.getMotor().setQtdPist((int) leitura.entDadosNumericosObrigatorios("Digite a quantiadade de pistões do motor:"));
		veiculo.getMotor().setPotencia((int) leitura.entDadosNumericosObrigatorios("Digite a potência do motor:"));
	}

	private static void validateVelocMax(float velocMax) throws VelocException {
		if (velocMax < 80 || velocMax > 110) {
			throw new VelocException();
		}
	}

	private static void populatePasseioEpecializationData(Passeio passeio) {
		passeio.setQtdePassageiros((int) leitura.entDadosNumericosObrigatorios("Digite a capacidade de passageiros do veículo:"));
	}

	private static void populateCargaEpecializationData(Carga carga) {
		carga.setTara((int) leitura.entDadosNumericosObrigatorios("Digite a tara do veículo:"));
		carga.setCargaMax((int) leitura.entDadosNumericosObrigatorios("Digite a carga máxima do veículo:"));
	}

	// Exibição de veículos

	private static void findAndPrintVeiculosOf(Class<? extends Veiculo> typeOf) {
		List<? extends Veiculo> listVeiculos = null;

		if (Passeio.class.equals(typeOf)) {
			listVeiculos = bdVeiculos.listPasseio();
		} else if (Carga.class.equals(typeOf)) {
			listVeiculos = bdVeiculos.listCarga();
		}

		if (listVeiculos.isEmpty()) {
			err("Nenhum veículo está cadastrado.");
			return;
		}

		out("\nOs seguintes veículos estão cadastrados:");
		listVeiculos.forEach(Teste::formatAndPrintVeiculo);
	}

	private static void findAndPrintVeiculosByPlaca(Class<? extends Veiculo> typeOf) {
		String placa = leitura.entDadosObrigatorios("Digite a placa para busca:");

		List<? extends Veiculo> locatedVeiculos = null;

		if (Passeio.class.equals(typeOf)) {
			locatedVeiculos = bdVeiculos.findPasseioByPlaca(placa);
		} else if (Carga.class.equals(typeOf)) {
			locatedVeiculos = bdVeiculos.findCargaByPlaca(placa);
		}

		if (locatedVeiculos.isEmpty()) {
			err("Nenhum veículo localizado.");
			return;
		}

		out("\nOs seguintes veículos foram localizados:");
		locatedVeiculos.forEach(Teste::formatAndPrintVeiculo);
	}

	private static void formatAndPrintVeiculo(Veiculo veiculo) {
		out("\t" + veiculo.toString()
				.replaceAll(
						System.lineSeparator(),
						System.lineSeparator() + "\t"));
	}

	private static void out(String out) {
		System.out.println(out);
	}

	private static void err(String err) {
		System.err.println(err);
	}
}
