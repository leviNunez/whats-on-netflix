# whats-on-netflix-app
An app that lets you browse Netflix's content by region/country without a subscription using the uNoGS (unofficial Netflix online Global Search) api from https://rapidapi.com/.

I used the MVVM pattern to separate the application's business and presentation logic from its user interface. 

When first launched, a region must be selected.

<img width="276" alt="Screen Shot 2023-02-08 at 3 54 08 PM" src="https://user-images.githubusercontent.com/81485281/217637493-5a24527a-98cc-49f6-bb5d-c24a939ec3ff.png"> <img width="276" alt="image" src="https://user-images.githubusercontent.com/81485281/217646594-202a0061-328e-4a3b-bfe9-0d31701a6c25.png">


The Home screen presents the content divided by 4 categories: New Releases, TV Shows, Movies and Leaving Soon.

<img width="276" alt="image" src="https://user-images.githubusercontent.com/81485281/217645011-0314a3fa-c430-41e0-8d7c-748fae0dbf0b.png"> <img width="276" alt="image" src="https://user-images.githubusercontent.com/81485281/217645307-cf753056-4843-4a38-b752-6643350a3836.png">


Each item can be viewed in detail by tapping on its thumbnail.

<img width="276" alt="Screen Shot 2023-02-08 at 3 52 57 PM" src="https://user-images.githubusercontent.com/81485281/217637615-4a2f7b98-4494-45c4-a3d2-11c6d2383950.png"> <img width="276" alt="image" src="https://user-images.githubusercontent.com/81485281/217645766-451cb7b2-0b48-4478-b6d1-665ec7dcccf4.png">


Content can be searched by title using the search bar. 

<img width="276" alt="Screen Shot 2023-02-08 at 3 53 46 PM" src="https://user-images.githubusercontent.com/81485281/217637762-e9016ec0-f2bc-4792-9330-df41047e66f7.png"> <img width="276" alt="Screen Shot 2023-02-08 at 4 13 19 PM" src="https://user-images.githubusercontent.com/81485281/217641004-604f51f7-662d-4f9e-bde6-a00054bd8a27.png">

Each query is stored in a data base and then filtered as the user types.

<img width="276" alt="Screen Shot 2023-02-08 at 3 53 32 PM" src="https://user-images.githubusercontent.com/81485281/217637682-0f68d8bf-3dde-45e6-9ca2-def11238c753.png"> <img width="276" alt="Screen Shot 2023-02-08 at 4 08 04 PM" src="https://user-images.githubusercontent.com/81485281/217640369-e0aecd3b-e0a6-49de-8776-306ffd0ee7c2.png">

The region can be changed at any time through the settings menu. Doing so will refresh the content list.

<img width="276" alt="Screen Shot 2023-02-08 at 3 53 20 PM" src="https://user-images.githubusercontent.com/81485281/217642606-1eaa2971-f398-4be2-922b-4b05c804ec00.png"> <img width="276" alt="Screen Shot 2023-02-08 at 4 20 07 PM" src="https://user-images.githubusercontent.com/81485281/217642631-9a0b39c4-6a77-41e8-98f5-2b8d84f51fd5.png">

Dark mode is also supported.

<img width="276" alt="Screen Shot 2023-02-08 at 4 24 41 PM" src="https://user-images.githubusercontent.com/81485281/217643394-3317d732-7235-46e0-97db-df74a5e2ad4a.png"> <img width="276" alt="Screen Shot 2023-02-08 at 4 26 00 PM" src="https://user-images.githubusercontent.com/81485281/217643433-f1ad7453-6bd6-44b8-9013-6d04ec642a46.png">
