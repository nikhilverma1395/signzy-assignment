angular
    .module('MyApp', ['ngMaterial', 'ngMessages', 'material.svgAssetsCache'])
    .controller('DemoCtrl', function ($scope, $http) {
            $scope.display = function (query) {
                $http.get("http://localhost:8090/search?query=" + query)
                    .then(function (response) {

                        $scope.response_titles = response.data;
                    });
            }
        }
    );
