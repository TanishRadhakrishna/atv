// Predefined document with text content
const documentContent = `Hello, my email is example@example.com.
You can reach me at test@test.com.
This is a sample text file.
Contact us at support@domain.com or visit our website at www.example.com.
For inquiries, reach out to info@domain.com.

Here are some phone numbers:
- John Doe: (555) 123-4567
- Jane Smith: +1-800-555-0199
- Customer Support: 1-800-555-0123
- Emergency: 911

Some URLs for reference:
- https://www.google.com
- http://example.org/path/to/resource?query=123
- ftp://ftp.example.com/resource.txt
- www.testsite.com

Random quotes:
"To be or not to be, that is the question." - William Shakespeare
"All that glitters is not gold." - William Shakespeare
"Life is what happens when you're busy making other plans." - John Lennon

Additional emails for testing:
alice@example.com
bob.smith@company.co.uk
charlie_brown@school.edu

A mix of sentences with different formats:
The quick brown fox jumps over the lazy dog.
The rain in Spain stays mainly in the plain.
12345 is a number, and so is 67890.
Regex can be tricky but powerful!

Dates in various formats:
- 2024-01-15 (YYYY-MM-DD)
- 01/15/2024 (MM/DD/YYYY)
- January 15, 2024 (Month DD, YYYY)

More complex sentences:
The price is $19.99 and it was released on 12/12/2023.
My favorite numbers are 42 and 3.14159.
Is this the real life? Is this just fantasy?
The quick brown fox jumps over the lazy dog.

HTML-like content:
<div class="container">This is a div element.</div>
<a href="https://www.example.com">Visit Example</a>
<p>This is a paragraph.</p>
`;

document.getElementById('searchButton').addEventListener('click', function() {
    const regexInput = document.getElementById('regexInput').value;

    if (!regexInput) {
        alert("Please enter a regex pattern.");
        return;
    }

    // Perform regex search
    const regex = new RegExp(regexInput);
    
    const results = [];
    
    // Split content into lines and search for matches
    documentContent.split('\n').forEach((line, index) => {
        if (regex.test(line)) {
            results.push(`Match found: ${line}`);
        }
    });

    // Display results
    const resultDisplay = document.getElementById('results');
    
    if (results.length > 0) {
        resultDisplay.textContent = results.join('\n');
    } else {
        resultDisplay.textContent = 'No matches found.';
    }
});
