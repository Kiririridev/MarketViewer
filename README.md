# MarketViewer
Simple Android Application importing objects from JSON and putting them in ListView

Application retrieves object from JSON on server, wraps them into Adapter and puts into ListView.

Importing JSON from server is done in another threads. If side threads are not finished before creating ListView,
old JSONObjects are imported from Assets folder. Click on any button to refresh ListView.
