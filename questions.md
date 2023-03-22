# Questions and future work
*  Write Abstract base class to represent all the models to represent common attrbutes such as 'createdBy', 'createdDate', 'lastModifiedBy', 'lastModifiedDate' and we can make it Serializable
*  Implement authorization layer for the API endpoints
*  Write more test cases to increase code coverage
*  We can pass city as query parameter instead of post body, so that query dsl can be used to filter out tax rates based on the city
*  Add tax free days to the application.property file and make the application more configurable