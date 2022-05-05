package ru.otus.filler;

import org.springframework.stereotype.Service;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.AddressRepository;
import ru.otus.crm.repository.PhoneRepository;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.Optional;
import java.util.Random;

@Service
public class InitialDataFiller {

    private final DbServiceClientImpl dbServiceClient;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

    public InitialDataFiller(DbServiceClientImpl dbServiceClient,
                             AddressRepository addressRepository,
                             PhoneRepository phoneRepository) {
        this.dbServiceClient = dbServiceClient;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
    }

    public void action() {
        Random rnd = new Random(1_999_999_999);
        for (int idx=1; idx<11; idx++) {
            String name = "Clint"+idx;
            Optional<Client> byName = dbServiceClient.findByName(name);
            if(byName.isPresent()){
                continue;
            }
            Client client = dbServiceClient.saveClient(new Client(name));
            String nnn = client.getName()+" id:"+client.getId();
            String tel = String.valueOf(rnd.nextInt());
            phoneRepository.save( new Phone(tel +" - "+ nnn, client.getId() ) );
            addressRepository.save( new Address("Address for " + nnn, client.getId()) );
        }
    }
}
