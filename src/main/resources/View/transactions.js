/**
 * Created by eric on 12/4/16.
 */
angular.module("root", [])
	.controller("app", function($http) {
		var vm = this;

		$http.get("https://transaction-register.herokuapp.com/transactions")
			.then(function(response) {
				angular.forEach(response.data, function(value, key) {
					value["purchaseDate"] = new Date(value["purchaseDate"]);
				});
				vm.transactions = response.data;
			}, function(error) {
				console.log("Error")
			});
	});