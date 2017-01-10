/**
 * Created by eric on 12/4/16.
 */
angular.module("root", [])
	.controller("app", function($http) {
		var vm = this;

		$http.get("https://transaction-register.herokuapp.com/transactions")
			.then(function(response) {
				vm.transactions = response.data;
			}, function(error) {
				console.log("Error")
			});
	});