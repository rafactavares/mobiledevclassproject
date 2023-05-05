22266
Rafael Cunha Soares Tavares

TASK LIST:
1. Authentication 
1.1. Allow User to Signup V
1.2. Log In using username and password V 
1.3. Store userID once logged in to keep the user logged in (even after restarting the app) V
2. Product Listing 
2.1. List Product Categories V
2.2. On clicking a Category, list Products in that Category V
2.3. On clicking a Product, show Product description, show buy button and controls to change quantity. v
3. Cart 
3.1. Show cart summary V
3.2. Show total amount V
3.3. Purchase button to place an order, show order notification V
4. Show order history 
4.1. List users orders V
4.2. On clicking an Order, show Order details and Products ordered V
4.3. On clicking a Product, take them to Product description page created for 2.3 V
5. Show User details 
5.1. Use the stored userID to show user details V
5.2. Show a random circular profile image V
5.3. Show Logout button, on click take back to Signup / Log In page (Restart should not auto login after logout) V
6. UI/Implementational Requirements 
6.1. RecyclerView used for all Lists: Categories, Products, Orders V 
6.2. If logged in, attach authentication token to all requests until logout V
6.3. Add a small "About this app" button in the profile page, that shows a page on click with your copyright details and credits V
7. Bonus 
7.1. ViewPager2 with bottom TabLayout for: Shop, Cart, Orders, Profile icons V 
7.2. Show a map fragment based on the GPS co-ordinates in the user profile V




REPORT: 
The implementation of the fake API was a pain, and I had to look for countless solutions. I tried using the machine's recycler and adapter without success. The Manifest was another issue that needed to be addressed. 

Designing the user interface was one of the difficulties I ran into when creating my shopping app. At first, I had trouble coming up with a design that was both user-friendly and aesthetically pleasing. I experimented with many layouts and design styles, but nothing seemed to work. Eventually, I asked my peers for comments and changed the design based on their suggestions to make it more user-friendly. In order to determine where I should put my focus and how to create better apps in the future, I also carried out some user experience tests with friends and housemates.

The key element, in my opinion, is user testing and feedback, which I discovered while creating the shopping app. User testing enables you to pinpoint problems and areas that want work. It's simple to get caught up in your own mind and believe that your app is ideal.  

I had problems with sluggish animations and poor load times when it came to performance. I addressed this by reducing the amount of data the app needed to load all at once and optimizing the code.
I learned a lot about the value of performance optimization, compatibility testing, and user interface design and testing while creating an Android Studio shopping app for a school project. It also showed me the benefit of leveraging helper libraries to enhance the usability and functionality of the app, such as Retrofit and Glide for image loading (which, in the end, I didn't use at all).

Also, I'd like to mention that this project was seriously hard to finish, I've to talk with another students and try to get a bit of knowledgment of each one! Seems like that we haven't been teached enough to develop this! Many youtube videos helped me as well, it was a really colletaneous of many parts of knowledge. 