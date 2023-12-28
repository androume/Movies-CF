$('#myMovies').on('click', function() {
  const loggedInUser = localStorage.getItem('loggedInUser')
  if (!loggedInUser) {
    console.error('User not logged in');
    return;
  }
  // AJAX call for fetching the user's watchlist
  $.ajax({
    url: `http://localhost:8080/api/user/watchlist?username=${loggedInUser}`,
    type: 'GET',
    dataType: 'json',
    xhrFields: {
      withCredentials: true
    }
  })
  .done(function(data) {
    // Clear existing movie list
    $('#myMovieContainer').empty();
    // Loop through the movies and append movie titles with remove button to the container
    for (let i = 0; i < data.movies.length; i++) {
      const movieTitle = data.movies[i].title;
      // Append movie title with remove button to a div container
      $('#myMovieContainer').append(`
        <div style="padding: 15px; display: flex; flex-direction: column; align-items: center;">
            <h3 style="margin-bottom: 5px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">${movieTitle}</h3>
            <button class="remove-movie" data-movie="${movieTitle}" style="align-self: center;">Remove</button>
        </div>
`);
    }
    // Add click event handler to remove buttons
    $('.remove-movie').on('click', function() {
      const movieToRemove = $(this).data('movie');
      const self = this;

      // AJAX call to remove a movie from the watchlist
      $.ajax({
        url: `http://localhost:8080/api/user/watchlist/${loggedInUser}/${movieToRemove}`,
        type: 'PUT',
        dataType: 'json'
      })
      .done(function(data) {
        console.log('Movie removed:', movieToRemove);
        // TO-DO: this part doesn't work, the page needs to be reloaded for the changes to take effect
        $(self).remove();
      })
      .fail(function(xhr, statusText, error) {
        console.error('Error removing movie:', error);
        console.log(xhr);
        console.log(statusText);
      });
    });
  })
  .fail(function(xhr, statusText, error) {
    console.error('Error getting watchlist:', error);
    console.log(xhr);
    console.log(statusText);
  });
});

$('#refreshButton').on('click', function() {
  location.reload();
});