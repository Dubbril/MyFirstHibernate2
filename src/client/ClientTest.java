package client;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import dao.AddressDAO;
import dao.BookDAO;
import dao.BorrowDetailDAO;
import dao.CustomerDAO;
import dao.ServiceDAO;
import model.Address;
import model.Book;
import model.BorrowDetail;
import model.Customer;
import model.CustomerVIP;
import model.Service;
import util.HibernateUtil;

public class ClientTest {

	public void SetDataTables() {
		Service service1 = new Service("Email");
		Service service2 = new Service("Surface mail");

		Book book1 = new Book("Basic Java", "For the beginner");
		Book book2 = new Book("Hibernate Java", "OR Mapping");
		Book book3 = new Book("JBoss", "Java Application Server : Open Source");

		try {
			HibernateUtil.beginTransaction();
			ServiceDAO servicedao = new ServiceDAO();
			servicedao.insert(service1);
			servicedao.insert(service2);

			BookDAO bookdao = new BookDAO();
			bookdao.insert(book1);
			bookdao.insert(book2);
			bookdao.insert(book3);
			HibernateUtil.commitTransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
			System.out.println("Can not add records into look up table");
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public void addCustomer() {
		System.out.println("########## Insert ##########");
		Customer cust1 = new CustomerVIP("JACK", "Marlet", 15, "Always");
		Customer cust2 = new Customer("YAN", "BILLANA", 20);
		Customer cust3 = new Customer("Tomaki", "JAKATA", 35);
		Address addr1 = new Address("30-32", "Latphroa", "BKK", "10310");
		Address addr2 = new Address("40-50", "Pahonyothin", "BKK", "11111");
		Address addr3 = new Address("55-89", "Bangna", "BKK", "12345");
		Address addr4 = new Address("54-84", "Bangkapi", "BKK", "34322");

		try {
			HibernateUtil.beginTransaction();
			ServiceDAO servicedao = new ServiceDAO();
			cust1.getService().add(servicedao.findByPK(1));
			cust1.getService().add(servicedao.findByPK(2));
			cust2.getService().add(servicedao.findByPK(2));
			cust3.getService().add(servicedao.findByPK(1));
			CustomerDAO custdao = new CustomerDAO();
			custdao.insert(cust1);
			custdao.insert(cust2);
			custdao.insert(cust3);

			AddressDAO addressdao = new AddressDAO();
			addr1.setCustomer(cust1);
			addr2.setCustomer(cust1);
			addr3.setCustomer(cust2);
			addr4.setCustomer(cust3);
			addressdao.insert(addr1);
			addressdao.insert(addr2);
			addressdao.insert(addr3);
			addressdao.insert(addr4);

			HibernateUtil.commitTransaction();

		} catch (Exception ex) {
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
			System.out.println("Can not records into customer,add,service,tables");
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void addBorrowDetial() {
		Date borrowdate = new Date(System.currentTimeMillis());
		Date duedate = new Date(System.currentTimeMillis() + 7 * 86400000);
		try {
			HibernateUtil.beginTransaction();
			CustomerDAO custdao = new CustomerDAO();
			Customer cust1 = custdao.findByPK(1);
			Customer cust2 = custdao.findByPK(2);
			Customer cust3 = custdao.findByPK(3);
			BookDAO bookdao = new BookDAO();
			Book book1 = bookdao.findByPK(1);
			Book book2 = bookdao.findByPK(2);

			BorrowDetail bookdetail1 = new BorrowDetail(cust1, null, borrowdate, duedate, book1);
			BorrowDetail bookdetail2 = new BorrowDetail(cust1, null, borrowdate, duedate, book2);
			BorrowDetail bookdetail3 = new BorrowDetail(cust2, null, borrowdate, duedate, book1);
			BorrowDetail bookdetail4 = new BorrowDetail(cust3, null, borrowdate, duedate, book2);

			BorrowDetailDAO borrowdetaildao = new BorrowDetailDAO();
			borrowdetaildao.insert(bookdetail1);
			borrowdetaildao.insert(bookdetail2);
			borrowdetaildao.insert(bookdetail3);
			borrowdetaildao.insert(bookdetail4);

			HibernateUtil.commitTransaction();

		} catch (Exception ex) {
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
			System.out.println("Can not insert data into BorrowDetail table");
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public void display() {
		try {
			HibernateUtil.beginTransaction();
			displayBook();
			displayService();
			dispalyTransaction();
			HibernateUtil.commitTransaction();

		} catch (Exception ex) {
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
			System.out.println("Can not dispaly Data");
		}
	}

	public void dispalyTransaction() throws Exception {
		System.out.println("--------------------------------------Table: Customer/Service/Address/BorrowDetail"
				+ "--------------------------------------");
		CustomerDAO custdao = new CustomerDAO();
		List<Customer> custlist = custdao.findAll();

		if (custlist != null) {
			Iterator<Customer> custiter = custlist.iterator();
			int no = 1;
			while (custiter.hasNext()) {
				Customer cust = (Customer) custiter.next();
				System.out.println("-----------------------------Customer" + no + ":-----------------------------");
				no++;
				System.out.println("CustomerId: " + cust.getId() + ", CustomerName: " + cust.getFirstname() + " "
						+ cust.getSurname() + ",Age: " + cust.getAge());
				Set<Service> services = cust.getService();
				if (services != null) {
					Iterator<Service> serviceiter = services.iterator();
					while (serviceiter.hasNext()) {
						Service service = (Service) serviceiter.next();
						System.out.println("ServiceDescription: " + service.getDes());
					}
				} else {
					System.out.println("Customer has not registered any service");
				}

				AddressDAO addressdao = new AddressDAO();
				List<Address> addrlist = addressdao.findByCustId(cust.getId());
				if (addrlist != null) {
					Iterator<Address> serviceiter = addrlist.iterator();
					while (serviceiter.hasNext()) {
						Address address = (Address) serviceiter.next();
						System.out.println("AddressId: " + address.getId() + ", " + "Province: " + address.getProvince()
								+ ",Road: " + address.getRoad() + ",ZipCode: " + address.getZipcode());
					}
				} else {
					System.out.println("There is no address info for this customer");
				}

				BorrowDetailDAO borrowdetaildao = new BorrowDetailDAO();
				List<BorrowDetail> borrowdetaillist = borrowdetaildao.findByCustId((int) cust.getId());
				if (borrowdetaillist != null) {
					Iterator<BorrowDetail> borrowdetailiter = borrowdetaillist.iterator();
					while (borrowdetailiter.hasNext()) {
						BorrowDetail borrowdetial = (BorrowDetail) borrowdetailiter.next();
						System.out.println("BookName: " + borrowdetial.getBook().getTitle() + "DueDate: "
								+ borrowdetial.getDuedate());
					}
				} else {
					System.out.println("There is no address info for this customer");
				}
			}
		} else {
			System.out.println("There is no data in customer Table");
		}

	}

	public void displayService() throws Exception {
		System.out.println(
				"--------------------------------------Table: Service" + "--------------------------------------");
		ServiceDAO servicedao = new ServiceDAO();
		List<Service> serveicelist = servicedao.findAll();
		if (serveicelist != null) {
			Iterator<Service> serviceiter = serveicelist.iterator();
			while (serviceiter.hasNext()) {
				Service service = (Service) serviceiter.next();
				System.out.println("Service: " + service.getId() + ", " + "Description: " + service.getDes());
			}
		} else {
			System.out.println("There is no data in Book Table");
		}
	}

	public void displayBook() throws Exception {
		System.out.println(
				"--------------------------------------Table: Book" + "--------------------------------------");
		BookDAO bookdao = new BookDAO();
		List<Book> booklist = bookdao.findAll();
		if (booklist != null) {
			Iterator<Book> bookiter = booklist.iterator();
			while (bookiter.hasNext()) {
				Book book = (Book) bookiter.next();
				System.out.println("BookId: " + book.getId() + ", " + "BookTitle: " + book.getTitle()
						+ ", Description: " + book.getDescription());
			}
		} else {
			System.out.println("There is no data in Book Table");
		}

	}

	public void update() {
		CustomerDAO custdao = new CustomerDAO();
		AddressDAO addrdao = new AddressDAO();
		ServiceDAO servicedao = new ServiceDAO();
		BookDAO bookdao = new BookDAO();
		try {
			HibernateUtil.beginTransaction();

			Service service = servicedao.findByPK(1);
			service.setDes("CHANGE");

			Book book = bookdao.findByPK(1);
			book.setTitle("CHANGE");

			Address addr = addrdao.findByPK(1);
			addr.setZipcode("CHANGE");

			Customer cust = custdao.findByPK(1);
			cust.setAge(33);
			cust.setFirstname("CHANGE");

			custdao.update(cust);
			addrdao.update(addr);

			HibernateUtil.commitTransaction();

		} catch (Exception ex) {
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
			System.out.println("Can not update Data");
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public void delete() {
		CustomerDAO custdao = new CustomerDAO();
		try {
			HibernateUtil.beginTransaction();
			Customer cust = custdao.findByPK(1);
			custdao.delete(cust);
			cust = custdao.findByPK(2);
			custdao.delete(cust);
			cust = custdao.findByPK(3);
			custdao.delete(cust);
			HibernateUtil.commitTransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
			System.out.println("Can not delete Data");
		}
	}

	public static void main(String[] args) {
		ClientTest test = new ClientTest();
		test.SetDataTables();
		test.addCustomer();
		test.addBorrowDetial();
		// test.update();
		test.delete();
		test.display();
	}

}
