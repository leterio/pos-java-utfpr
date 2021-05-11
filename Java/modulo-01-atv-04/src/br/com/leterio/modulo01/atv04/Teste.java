package br.com.leterio.modulo01.atv04;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Teste {
	private static final VeiculoDB<Passeio> VEICULOS_PASSEIO = new VeiculoDB<>(5, "passeio", Passeio.class);
	private static final VeiculoDB<Carga> VEICULOS_CARGA = new VeiculoDB<>(5, "carga", Carga.class);
	private static final Leitura leitura = new Leitura(); /* neste contexto/arquitetura, deveria ser estática (helper/util) */

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
						menuInsertVeiculoOn(VEICULOS_PASSEIO);
						break;
					case 2:
						menuInsertVeiculoOn(VEICULOS_CARGA);
						break;
					case 3:
						findAndPrintVeiculosOf(VEICULOS_PASSEIO);
						break;
					case 4:
						findAndPrintVeiculosOf(VEICULOS_CARGA);
						break;
					case 5:
						findAndPrintVeiculosByPlacaOf(VEICULOS_PASSEIO);
						break;
					case 6:
						findAndPrintVeiculosByPlacaOf(VEICULOS_CARGA);
						break;
					case 7:
						out("Obrigado e até logo ;D");
						return;
					default:
						err("Você selecionou uma opção inválida.");
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	private static void menuInsertVeiculoOn(VeiculoDB<?> veiculoDB) {
		do {
			insertVeiculoOn(veiculoDB);

			menuInsertVeiculoOnLoop: while (true) {
				out("\nDeseja inserir outro veículo do tipo " + veiculoDB.getDescricaoTipo() + "?");
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

	private static <T extends Veiculo> void insertVeiculoOn(VeiculoDB<T> veiculoDB) {
		veiculoDB.checkStorage();

		T veiculo = instantiateVeiculo(veiculoDB);

		readDataFor(veiculo);

		veiculoDB.add(veiculo);

		out("Veículo cadastrado");
		formatAndPrintVeiculo(veiculo);
	}

	private static <T extends Veiculo> T instantiateVeiculo(VeiculoDB<T> veiculoDB) {
		try {
			return veiculoDB.getTypeOf().getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new Error("Falhou ao instanciar um objeto para veículo do tipo " + veiculoDB.getDescricaoTipo());
		}
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
		veiculo.setVelocMax((float) leitura.entDadosNumericosObrigatorios("Digite a velocidade máxima do veículo (KM/h):"));
		veiculo.setMotor(new Motor());
		veiculo.getMotor().setQtdPist((int) leitura.entDadosNumericosObrigatorios("Digite a quantiadade de pistões do motor:"));
		veiculo.getMotor().setPotencia((int) leitura.entDadosNumericosObrigatorios("Digite a potência do motor:"));
	}

	private static void populatePasseioEpecializationData(Passeio passeio) {
		passeio.setQtdePassageiros((int) leitura.entDadosNumericosObrigatorios("Digite a capacidade de passageiros do veículo:"));
	}

	private static void populateCargaEpecializationData(Carga carga) {
		carga.setTara((int) leitura.entDadosNumericosObrigatorios("Digite a tara do veículo:"));
		carga.setCargaMax((int) leitura.entDadosNumericosObrigatorios("Digite a carga máxima do veículo:"));
	}

	// Exibição de veículos

	private static void findAndPrintVeiculosOf(VeiculoDB<?> veiculoDB) {
		List<? extends Veiculo> listVeiculos = veiculoDB.list();

		if (listVeiculos == null || listVeiculos.isEmpty()) {
			err("Nenhum veículo de " + veiculoDB.getDescricaoTipo() + " está cadastrado.");
			return;
		}

		out("\nOs seguintes veículos de " + veiculoDB.getDescricaoTipo() + " estão cadastrados:");
		listVeiculos.forEach(Teste::formatAndPrintVeiculo);
	}

	private static void findAndPrintVeiculosByPlacaOf(VeiculoDB<?> veiculoDB) {
		String placa = leitura.entDadosObrigatorios("Digite a placa para busca:");

		List<? extends Veiculo> locatedVeiculos = veiculoDB.findByPlaca(placa);

		if (locatedVeiculos.isEmpty()) {
			err("Nenhum veículo de " + veiculoDB.getDescricaoTipo() + " localizado.");
			return;
		}

		out("\nOs seguintes veículos de " + veiculoDB.getDescricaoTipo() + " foram localizados:");
		locatedVeiculos.forEach(Teste::formatAndPrintVeiculo);
	}

	private static void formatAndPrintVeiculo(Veiculo veiculo) {
		out("\t" + veiculo.toString()
				.replaceAll(
						System.lineSeparator(),
						System.lineSeparator() + "\t"));
	}

	// Storage
	private static final class VeiculoDB<T extends Veiculo> {
		private final List<T> storage;
		private final Class<T> typeOf;
		private final int storageLimit;
		private final String descricaoTipo;

		public VeiculoDB(int storageLimit, String descricaoTipo, Class<T> typeOf) {
			this.storage = new ArrayList<>();
			this.storageLimit = storageLimit;
			this.descricaoTipo = descricaoTipo;
			this.typeOf = typeOf;
		}

		public void checkStorage() {
			if (storage.size() >= storageLimit) {
				throw new RuntimeException("O limite de veículos cadastrados já foi atingido.");
			}
		}

		public String getDescricaoTipo() {
			return descricaoTipo;
		}

		public Class<T> getTypeOf() {
			return typeOf;
		}

		public List<T> list() {
			return Collections.unmodifiableList(storage);
		}

		public List<T> findByPlaca(String placa) {
			return storage.stream()
					.filter(v -> Objects.nonNull(v.getPlaca()))
					.filter(v -> v.getPlaca().equalsIgnoreCase(placa))
					.collect(Collectors.toList());
		}

		public void add(T veiculo) {
			checkStorage();
			checkPreconditions(veiculo);
			storage.add(veiculo);
		}

		private void checkPreconditions(T newVeiculo) {
			if (!findByPlaca(newVeiculo.getPlaca()).isEmpty()) {
				throw new RuntimeException("Veículo com placa " + newVeiculo.getPlaca() + " já cadastrado.");
			}
		}
	}

	private static void out(String out) {
		System.out.println(out);
	}

	private static void err(String err) {
		System.err.println(err);
	}
}
