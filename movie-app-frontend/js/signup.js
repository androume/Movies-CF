$(document).ready(function() {

    $("#signupForm").submit(function(event) {
        event.preventDefault();
  
        // Get form data
        const formData = {
            username: $("#username").val(),
            password: $("#password").val()
        };
  
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/signup/create",
            data: formData,
            dataType: "json",
            encode: true,
            xhrFields: {
              withCredentials: true
            },
            success: function(data) {
                console.log("Account created successfully");
            },
            error: function() {
                console.log("Error creating account");
            }
        });
    });
})

