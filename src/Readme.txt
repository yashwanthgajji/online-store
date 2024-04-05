How to run code:
    -	Unzip the code from zip file.
    -	Upload code to server "in-csci-rrpc03.cs.iupui.edu".
    -	In cmd/shell, go to the folder which has 'online-store.jar'.
    -	To compile and create jar file again run below commands:
        -	make clean
        -	make compile
        -	make package
    -	‘online-store.jar’ will be created.
    -	Now to run server, follow any of two commands:
        -	java -cp online-store.jar server.RemoteStore
        -	make run-server
    -	Now to run-client, follow any of two commands:
        -	java -cp online-store.jar client.views.StoreView
        -	make run-client

Package structure:
    -	Code contains 3 main packages.
        o	server – server related code
        o	common – common files for both server and client
        o	client – client related code.
    -	Common package contains.
        o	enums – enums used in the code
            	UserRole.java
        o	models – all the entity models.
            	User.java
            	Product.java
            	CartItem.java
        o	services – all the service points given to client
            	UserService.java
            	ProductService.java
            	CartService.java
    -	server package contains.
        o	repos – all the repositories
            	UserRepo.java
            	ProductRepo.java
            	CartRepo.java
        o	serviceImpls – all the implementations of services
            	UserServiceImpl.java
            	ProductServiceImpl.java
            	CartServiceImpl.java
    -	client package contains.
        o	controllers – all the view controllers are here
            	UserController.java
            	ProductController.java
            	CartController.java
        o	views – user interface code goes here
            	StoreView.java

Brief Description:
	common
    -	common/ServerDetails.java
        o	Has Server url and stub names for end points are stored in this file to make it commonly available for both server and client.
    -	common/enums/UserRole.java
        o	role of the user (ADMIN or CUSTOMER)
    -	common/model/User.java
        o	Entity class to store user data like name, email, phone and role(UserRole)
    -	common/model/Product.java
        o	Entity class to store product data like name, description, price and quantity available.
    -	common/model/CartItem.java
        o	When a product is selected by user, its corresponding CartItem is created with cartItemID, productid and quantity in cart.
    -	common/services/UserService.java
        o	Interface with all user related functionalities endpoint available to client.
    -	common/services/ProductService.java
        o	Interface with all product related functionalities endpoint available to client.
    -	common/services/CartService.java
        o	Interface with all cart related functionalities endpoint available to client.
    server
    -	server/repos/UserRepo.java
        o	Stored users in a Concurrent Hash map (which is serializable) and all the crud operations are in this file.
    -	server/repos/ProductRepo.java
        o	Stored products in a Concurrent Hash map (which is serializable) and all the crud operations are in this file.
    -	server/repos/CartRepo.java
        o	The One to Many relationship between User and CartItem is stored in this CartRepo class in a Concurrent Hash map of userID and their corresponding cart items.
    -	server/serviceImpls/UserServiceImpl.java
        o	Implements UserService interface and consists the business logic.
    -	server/serviceImpls/ProductServiceImpl.java
        o	Implements ProductService interface and consists the business logic.
    -	server/serviceImpls/CartServiceImpl.java
        o	Implements CartService interface and consists the business logic.
    -	server/RemoteStore.java
        o	RMI code to bind the services into the registry.
    client
    -	client/views/StoreView.java
        o	User interface and the interations of user with command line are handled here.
        o	Authorization for functionalities are also handled in the view.
        o	Depending on the logged in user, UI will change
            	If user is a customer, he sees the products on dashboard and he can add them to cart and buy them.
            	Else if user is admin, he see the products customers where he can add, update, remove the products or customers from database.
        o	A sessionID is stored to store userID throughout his logged session which is used to determine whether he is customer or admin.
    -	client/controllers/UserController.java
        o	Controller binds the user functionalities between UserService and StoreView.
    -	client/controllers/ProductController.java
        o	Controller binds the product functionalities between ProductService and StoreView.
    -	client/controllers/CartController.java
        o	Controller binds the cart functionalities between CartService and StoreView.
