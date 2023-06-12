package com.sanjit.springbootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class SpringBootExampleApplication {

	private final CustomerRepository customerRepository;

	public SpringBootExampleApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleApplication.class, args);
	}

	@GetMapping("/")
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	record NewCustomerRequest(String name, Integer age) {}

	@PostMapping("/")
	public void addCustomer(@RequestBody NewCustomerRequest newCustomerRequest) {
		Customer customer = new Customer();
		customer.setAge(newCustomerRequest.age);
		customer.setName(newCustomerRequest.name);
		customerRepository.save(customer);
	}

	@DeleteMapping("/")
	public Customer deleteCustomer(Integer id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer != null) {
			customerRepository.deleteById(id);
		}
		return customer;
	}

	@GetMapping("/greet")
	public GreetResponse greet() {
		return new GreetResponse("Hello", List.of("Java", "Python", "C++"), new Person("Sanjit"));
	}

	record Person(String name) {}

	record GreetResponse(String name, List<String> favP, Person p) {}

	/*
	View running services -:
sudo service --status-all

Install Docker in ubuntu -:
sudo apt install apt-transport
sudo apt install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
apt-cache policy docker-ce
sudo apt install docker-ce
sudo systemctl status docker
systemctl start docker
sudo systemctl status docker

Giving root permission to user -:
sudo usermod -a -G docker $USER

Restart new session for docker -:
newgrp docker

To start docker service for project with configurations in docker-compose.yml -:
docker compose up -d

View if docker service is connected to postgres user -:
docker compose ps
docker logs postgres -f
	 */
}
