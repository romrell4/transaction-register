/**
 * Created by eric on 12/4/16.
 */
angular.module("root", [])
	.controller("index", function($http) {
		var vm = this;

		$http.get("https://transaction-register.herokuapp.com/categories/active")
			.then(function(response) {
				vm.categories = response.data;
			}, function(error) {
				console.log("Error");
			});

		$http.get("https://transaction-register.herokuapp.com/categories/months")
			.then(function(response) {
				vm.months = response.data;
			}, function(error) {
				console.log("Error");
			});



		vm.isGreen = function(category) {
			console.log(category);
			return category.amountBudgeted != 0 && category.amountSpent <= category.amountBudgeted;
		};
		vm.isRed = function(category) {
			return category.amountBudgeted != 0 && category.amountSpent > category.amountBudgeted;
		};
	});