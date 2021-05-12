package br.com.leterio.modulo01.atv06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BDVeiculos {
	private static final int PASSEIO_CAPACITY = 5;
	private static final int CARGA_CAPACITY = 5;

	private final List<Passeio> passeioDB = new ArrayList<>(PASSEIO_CAPACITY);
	private final List<Carga> cargaDB = new ArrayList<>(CARGA_CAPACITY);

	public List<Passeio> listPasseio() {
		return Collections.unmodifiableList(passeioDB);
	}

	public List<Carga> listCarga() {
		return Collections.unmodifiableList(cargaDB);
	}

	public List<Passeio> findPasseioByPlaca(String placa) {
		return passeioDB.stream()
				.filter(v -> Objects.nonNull(v.getPlaca()))
				.filter(v -> v.getPlaca().equalsIgnoreCase(placa))
				.collect(Collectors.toList());
	}

	public List<Carga> findCargaByPlaca(String placa) {
		return cargaDB.stream()
				.filter(v -> Objects.nonNull(v.getPlaca()))
				.filter(v -> v.getPlaca().equalsIgnoreCase(placa))
				.collect(Collectors.toList());
	}

	public void canAddPasseio() throws BDVeiculoCapacityException {
		checkStorage(passeioDB, PASSEIO_CAPACITY);
	}

	public void addPasseio(Passeio passeio) throws BDVeiculoCapacityException, VeicExistException {
		canAddPasseio();
		checkContraints(passeio);
		passeioDB.add(passeio);
	}

	public void canAddCarga() throws BDVeiculoCapacityException {
		checkStorage(cargaDB, PASSEIO_CAPACITY);
	}

	public void addCarga(Carga carga) throws BDVeiculoCapacityException, VeicExistException {
		canAddCarga();
		checkContraints(carga);
		cargaDB.add(carga);
	}

	private void checkStorage(List<? extends Veiculo> storage, int maxCapacity) throws BDVeiculoCapacityException {
		if (storage.size() >= maxCapacity) {
			throw new BDVeiculoCapacityException();
		}
	}

	private void checkContraints(Veiculo veiculo) throws VeicExistException {
		if (veiculo instanceof Passeio && !findPasseioByPlaca(veiculo.getPlaca()).isEmpty()
				|| veiculo instanceof Carga && !findCargaByPlaca(veiculo.getPlaca()).isEmpty()) {
			throw new VeicExistException();
		}
	}
}
