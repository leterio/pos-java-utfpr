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
	private static final Leitura leitura = new Leitura(); /* neste contexto/arquitetura, deveria ser est�tica (helper/util) */

	public static void main(String[] args) {
		menu();
	}

	// Menus

	private static void menu() {
		while (true) {
			out("\nSistema de Gest�o de Ve�culos - Menu Inicial");
			out("1. Cadastrar Ve�culo de Passeio");
			out("2. Cadastrar Ve�culo de Carga");
			out("3. Imprimir Todos os Ve�culos de Passeio");
			out("4. Imprimir Todos os Ve�culos de Carga");
			out("5. Imprimir Ve�culo de Passeio pela Placa");
			out("6. Imprimir Ve�culo de Carga pela Placa");
			out("7. Sair do Sistema");

			try {
				switch ((int) leitura.entDadosNumericosObrigatorios("> Digite a sua op��o:") /* Apenas a parte inteira */) {
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
						out("Obrigado e at� logo ;D");
						return;
					default:
						err("Voc� selecionou uma op��o inv�lida.");
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
				out("\nDeseja inserir outro ve�culo do tipo " + veiculoDB.getDescricaoTipo() + "?");
				out("1. Sim");
				out("2. N�o");

				switch ((int) leitura.entDadosNumericosObrigatorios("> Digite a sua op��o:")) {
					case 1:
						break menuInsertVeiculoOnLoop;
					case 2:
						return;
					default:
						err("Voc� selecionou uma op��o inv�lida.");
				}
			}
		} while (true);
	}

	// Cadastro do ve�culo

	private static <T extends Veiculo> void insertVeiculoOn(VeiculoDB<T> veiculoDB) {
		veiculoDB.checkStorage();

		T veiculo = instantiateVeiculo(veiculoDB);

		readDataFor(veiculo);

		veiculoDB.add(veiculo);

		out("Ve�culo cadastrado");
		formatAndPrintVeiculo(veiculo);
	}

	private static <T extends Veiculo> T instantiateVeiculo(VeiculoDB<T> veiculoDB) {
		try {
			return veiculoDB.getTypeOf().getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new Error("Falhou ao instanciar um objeto para ve�culo do tipo " + veiculoDB.getDescricaoTipo());
		}
	}

	private static <T extends Veiculo> void readDataFor(T veiculo) {
		out("\nDigite os dados do novo ve�culo:");

		if (veiculo instanceof Passeio) {
			populatePasseioEpecializationData((Passeio) veiculo);
		} else if (veiculo instanceof Carga) {
			populateCargaEpecializationData((Carga) veiculo);
		}

		veiculo.setPlaca(leitura.entDadosObrigatorios("Digite a placa do ve�culo:"));
		veiculo.setMarca(leitura.entDadosObrigatorios("Digite a marca do ve�culo:"));
		veiculo.setModelo(leitura.entDadosObrigatorios("Digite o modelo do ve�culo:"));
		veiculo.setVelocMax((float) leitura.entDadosNumericosObrigatorios("Digite a velocidade m�xima do ve�culo (KM/h):"));
		veiculo.setMotor(new Motor());
		veiculo.getMotor().setQtdPist((int) leitura.entDadosNumericosObrigatorios("Digite a quantiadade de pist�es do motor:"));
		veiculo.getMotor().setPotencia((int) leitura.entDadosNumericosObrigatorios("Digite a pot�ncia do motor:"));
	}

	private static void populatePasseioEpecializationData(Passeio passeio) {
		passeio.setQtdePassageiros((int) leitura.entDadosNumericosObrigatorios("Digite a capacidade de passageiros do ve�culo:"));
	}

	private static void populateCargaEpecializationData(Carga carga) {
		carga.setTara((int) leitura.entDadosNumericosObrigatorios("Digite a tara do ve�culo:"));
		carga.setCargaMax((int) leitura.entDadosNumericosObrigatorios("Digite a carga m�xima do ve�culo:"));
	}

	// Exibi��o de ve�culos

	private static void findAndPrintVeiculosOf(VeiculoDB<?> veiculoDB) {
		List<? extends Veiculo> listVeiculos = veiculoDB.list();

		if (listVeiculos == null || listVeiculos.isEmpty()) {
			err("Nenhum ve�culo de " + veiculoDB.getDescricaoTipo() + " est� cadastrado.");
			return;
		}

		out("\nOs seguintes ve�culos de " + veiculoDB.getDescricaoTipo() + " est�o cadastrados:");
		listVeiculos.forEach(Teste::formatAndPrintVeiculo);
	}

	private static void findAndPrintVeiculosByPlacaOf(VeiculoDB<?> veiculoDB) {
		String placa = leitura.entDadosObrigatorios("Digite a placa para busca:");

		List<? extends Veiculo> locatedVeiculos = veiculoDB.findByPlaca(placa);

		if (locatedVeiculos.isEmpty()) {
			err("Nenhum ve�culo de " + veiculoDB.getDescricaoTipo() + " localizado.");
			return;
		}

		out("\nOs seguintes ve�culos de " + veiculoDB.getDescricaoTipo() + " foram localizados:");
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
				throw new RuntimeException("O limite de ve�culos cadastrados j� foi atingido.");
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
				throw new RuntimeException("Ve�culo com placa " + newVeiculo.getPlaca() + " j� cadastrado.");
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
