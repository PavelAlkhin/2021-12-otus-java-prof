package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.model.dto.ClientRequestDto;
import ru.otus.crm.repository.AddressRepository;
import ru.otus.crm.repository.PhoneRepository;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.List;
import java.util.Optional;

@Service
public class AppService {
    private final DbServiceClientImpl dbServiceClient;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

    public AppService(DbServiceClientImpl dbServiceClient,
                      AddressRepository addressRepository,
                      PhoneRepository phoneRepository) {
        this.dbServiceClient = dbServiceClient;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
    }

    public Client saveUpdateClient(ClientRequestDto dto){
        Client client = getClientByName(dto.getName());
        addressRepository.save(new Address(dto.getAddress(), client.getId()));
        updateCreateAddress(dto.getAddress(), client.getId());
        phoneRepository.save(new Phone(dto.getPhone(), client.getId()));
        return client;
    }

    public List<Client> getAllClients(){
        return dbServiceClient.findAll();
    }

    private Client getClientByName(String name) {
        Optional<Client> byName = dbServiceClient.findByName(name);
        if (byName.isPresent()){
            return byName.get();
       }else {
            return dbServiceClient.saveClient(new Client(name));
       }
    }

    private Address updateCreateAddress(String street, Long clientId){
        List<Address> addressByClientId = addressRepository.findAddressByClientId(clientId);
        for (Address addr: addressByClientId){
            addressRepository.delete(addr);
        }
        return addressRepository.save(new Address(street, clientId));
    }

}
