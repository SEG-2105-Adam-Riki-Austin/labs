package main

import (
	"fmt"
	"io"
	"net/http"
)

type FetchResult struct {
	URL        string
	StatusCode int
	Size       int
	Error      error
}

func worker(id int, jobs <-chan string, results chan<- FetchResult) {
	for page := range jobs {
		fmt.Printf("Worker %d processing job %s\n", id, page)

		// Open request to remote
		resp, err := http.Get(page)
		if err != nil {
			results <- FetchResult{
				URL:        page,
				StatusCode: -1,
				Size:       -1,
				Error:      err,
			}
			continue
		}

		requestUrl := resp.Request.URL.String()

		// Get response size
		body := resp.Body
		size, err := io.Copy(io.Discard, body)
		body.Close()

		if err != nil {
			results <- FetchResult{
				URL:        requestUrl,
				StatusCode: resp.StatusCode,
				Size:       -1,
				Error:      err,
			}
			continue
		}

		// Send successful result to channel
		results <- FetchResult{
			URL:        requestUrl,
			StatusCode: resp.StatusCode,
			Size:       int(size),
			Error:      nil,
		}
	}
}

func main() {
	urls := []string{
		"https://example.com",
		"https://golang.org",
		"https://uottawa.ca",
		"https://github.com",
		"https://httpbin.org/get",
	}

	numWorkers := 3

	// Set up channels
	jobs := make(chan string, len(urls))
	results := make(chan FetchResult, len(urls))

	// Start workers
	for w := range numWorkers {
		go worker(w+1, jobs, results)
	}

	// Send URLs to fetch
	for i := range urls {
		jobs <- urls[i]
	}
	close(jobs)

	// Print results as they arrive
	for range urls {
		fmt.Println("Result:", <-results)
	}

	fmt.Println("\nScraping complete!")
}
