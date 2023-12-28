// Log-in script

  $(document).ready(function() {
  let loggedInUser; 
  
  $("#loginForm").submit(function(event) {
      event.preventDefault();

      // Get form data
      const formData = {
          username: $("#username").val(),
          password: $("#password").val()
      };

      localStorage.setItem('loggedInUser', formData.username);

      // Send the form data using AJAX
      $.ajax({
          type: "POST",
          url: "http://localhost:8080/api/login/authenticate",
          data: formData,
          dataType: "json",
          encode: true,
          xhrFields: {
            withCredentials: true
        }
      })
      .done(function(data) {
          // Store the logged-in user's username
          loggedInUser = formData.username;

          // Display a greeting message
          alert("Welcome " + loggedInUser);
      })
      .fail(function() {
          alert("Invalid username or password");
      });
  });

  // Searching for movies script

  const $button = $('.btn-search');
  const $input = $('.search-input');
  const $movieDetails = $('#movie-details');

  $button.on('click', () => {
    const title = $input.val();

    $.ajax({
      type: 'GET',
      url: `http://localhost:8080/api/movies/title?title=${title}&includePoster=true`,
      dataType: 'json'
    })
    .done(data => {
      $movieDetails.html(`
      <div style="background-color: white; border-radius: 5px; border: 2px solid black; padding: 15px;">
        <h2 style="margin-bottom: 5px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">${data.Title}</h2>
        <img src="${data.Poster}" alt="${data.Title} poster" style="margin-bottom: 5px;">
        <p style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">${data.Plot}</p>
        <p style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Directed by: ${data.Director}</p>
        <p style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Starring: ${data.Actors}</p>
      </div>
      `);

      const $watchlistButton = $('.watchlist-button');
      $(".watchlist-button").css("display", "block");

      $watchlistButton.on('click', () => {
        if (!loggedInUser) {
          console.error('User not logged in');
          return;
        }

        const requestData = {
          username: loggedInUser,
          director: data.Director,
          title: data.Title
        };

// Adding a movie to the logged in user's watchlist logic

        $.ajax({
          type: 'POST',
          url: `http://localhost:8080/api/users/movies/watchlist/${requestData.username}/${requestData.title}/${requestData.director}`,
          data: JSON.stringify(requestData),
          contentType: 'application/json',
          xhrFields: {
            withCredentials: true
          }
        })
        .done(() => {
          alert("Movie added to watchlist")
        })
        .fail((xhr, statusText, error) => {
          console.error(`Failed to add movie to watchlist: ${statusText}`);
          console.error(xhr);
          console.error(error);
          console.log(requestData);
        });
      });
    })
    .fail((xhr, statusText, error) => {
      console.error('Error getting movie data:', error);
      console.log(xhr);
      console.log(statusText);
    });
  });
});