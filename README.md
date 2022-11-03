# whats-on-netflix
An app that lets you browse Netflix's content without a subscription.

I used the uNoGS (unofficial Netflix online Global Search) api from https://rapidapi.com/.

The location is set to United States by default.

There are two tabs: One for series and another for movies.
Each tab loads the last 100 series/movies ordered by date.
Series and movies content can also be searched by title.

Tapping on the content thumbnail will load more details such as year, runtime, synopsis, etc.

Google's Room persistence library is used for offline caching.
