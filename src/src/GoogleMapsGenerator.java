package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GoogleMapsGenerator {

    public void generatePresentation(ArrayList<City> cities, String filename, boolean line) {
        String apiKey = "AIzaSyBIrqqR5a8-SBGKJfc-dnHwHqkroJwY1IU"; // Replace with your Google Maps API Key
        String fileName = filename + ".html";
        String fileNameWithLane = filename + "_car_lane.html";

        try {
            if (line) {
                generateGoogleMapsHTMLWithLine(apiKey, cities, fileName);
                generateGoogleMapsHTMLWithLane(apiKey, cities, fileNameWithLane);
                System.out.println("Generated Google Maps HTML file: " + fileNameWithLane);
            } else {
                generateGoogleMapsHTMLWithoutLine(apiKey, cities, fileName);
            }
            System.out.println("Generated Google Maps HTML file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error generating Google Maps HTML file: " + e.getMessage());
        }
    }

    public void generateGoogleMapsHTMLWithLine(String apiKey, ArrayList<City> cities, String fileName) throws IOException {
        StringBuilder locationData = new StringBuilder();
        for (City c : cities) {
            locationData.append(String.format("{lat: %.4f, lng: %.4f, name: '%s'},\n", c.getLatitude(), c.getLongitude(), c.getName()));
        }

        String htmlContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                  <title>Display Path and Names on Google Map</title>
                  <script src="https://maps.googleapis.com/maps/api/js?key=%s&callback=initMap" async defer></script>
                  <script>
                    var map;

                    function initMap() {
                      map = new google.maps.Map(document.getElementById('map'), {
                        center: {lat: 37.8333, lng: -98.5833},
                        zoom: 5
                      });

                      var locations = [
                        %s
                      ];

                      var pathCoordinates = locations.map(function(location) {
                        return new google.maps.LatLng(location.lat, location.lng);
                      });

                      var path = new google.maps.Polyline({
                        path: pathCoordinates,
                        geodesic: true,
                        strokeColor: '#FF0000',
                        strokeOpacity: 1.0,
                        strokeWeight: 2
                      });

                      path.setMap(map);

                      locations.forEach(function(location) {
                        var marker = new google.maps.Marker({
                          position: new google.maps.LatLng(location.lat, location.lng),
                          map: map
                        });

                        var infoWindow = new google.maps.InfoWindow({
                          content: location.name
                        });

                        marker.addListener('click', function() {
                          infoWindow.open(map, marker);
                        });
                      });
                    }
                  </script>
                  <style>
                    #map {
                      height: 100%%;
                    }
                    html, body {
                      height: 100%%;
                      margin: 0;
                      padding: 0;
                    }
                  </style>
                </head>
                <body>
                  <div id="map"></div>
                </body>
                </html>
                """, apiKey, locationData.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(htmlContent);
        }
    }

    public void generateGoogleMapsHTMLWithLane(String apiKey, ArrayList<City> cities, String fileName) throws IOException {
        StringBuilder locationData = new StringBuilder();
        for (City c : cities) {
            locationData.append(String.format("{lat: %.4f, lng: %.4f, name: '%s'},\n", c.getLatitude(), c.getLongitude(), c.getName()));
        }

        String htmlContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                  <title>Display Path and Names on Google Map</title>
                  <script src="https://maps.googleapis.com/maps/api/js?key=%s&callback=initMap" async defer></script>
                  <script>
                    var map;

                    function initMap() {
                      map = new google.maps.Map(document.getElementById('map'), {
                        center: {lat: 37.8333, lng: -98.5833},
                        zoom: 5
                      });

                      var locations = [
                        %s
                      ];

                      var directionsService = new google.maps.DirectionsService();
                           var directionsRenderer = new google.maps.DirectionsRenderer({map: map});
                     
                           var waypoints = locations.slice(1, locations.length - 1).map(function(location) {
                             return {location: new google.maps.LatLng(location.lat, location.lng)};
                           });
                     
                           directionsService.route({
                             origin: new google.maps.LatLng(locations[0].lat, locations[0].lng),
                             destination: new google.maps.LatLng(locations[locations.length - 1].lat, locations[locations.length - 1].lng),
                             waypoints: waypoints,
                             optimizeWaypoints: true,
                             travelMode: 'DRIVING'
                           }, function(response, status) {
                             if (status === 'OK') {
                               directionsRenderer.setDirections(response);
                             } else {
                               console.error('Directions request failed due to ' + status);
                             }
                           });
                     
                           locations.forEach(function(location) {
                             var marker = new google.maps.Marker({
                               position: new google.maps.LatLng(location.lat, location.lng),
                               map: map
                             });
                     
                             var infoWindow = new google.maps.InfoWindow({
                               content: location.name
                             });
                     
                             marker.addListener('click', function() {
                               infoWindow.open(map, marker);
                             });
                           });
                         }
                  </script>
                  <style>
                    #map {
                      height: 100%%;
                    }
                    html, body {
                      height: 100%%;
                      margin: 0;
                      padding: 0;
                    }
                  </style>
                </head>
                <body>
                  <div id="map"></div>
                </body>
                </html>
                """, apiKey, locationData.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(htmlContent);
        }
    }

    public void generateGoogleMapsHTMLWithoutLine(String apiKey, ArrayList<City> cities, String fileName) throws IOException {
        StringBuilder locationData = new StringBuilder();
        for (City c : cities) {
            locationData.append(String.format("{lat: %.4f, lng: %.4f, name: '%s'},\n", c.getLatitude(), c.getLongitude(), c.getName()));
        }

        String htmlContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                  <title>Display Path and Names on Google Map</title>
                  <script src="https://maps.googleapis.com/maps/api/js?key=%s&callback=initMap" async defer></script>
                  <script>
                    var map;

                    function initMap() {
                      map = new google.maps.Map(document.getElementById('map'), {
                        center: {lat: 37.8333, lng: -98.5833},
                        zoom: 5
                      });

                      var locations = [
                        %s
                      ];

                      locations.forEach(function(location) {
                             var marker = new google.maps.Marker({
                               position: new google.maps.LatLng(location.lat, location.lng),
                               map: map
                             });
                     
                             var infoWindow = new google.maps.InfoWindow({
                               content: location.name
                             });
                     
                             marker.addListener('click', function() {
                               infoWindow.open(map, marker);
                             });
                           });
                         }
                  </script>
                  <style>
                    #map {
                      height: 100%%;
                    }
                    html, body {
                      height: 100%%;
                      margin: 0;
                      padding: 0;
                    }
                  </style>
                </head>
                <body>
                  <div id="map"></div>
                </body>
                </html>
                """, apiKey, locationData.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(htmlContent);
        }
    }
}