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
		// Get response size
		size, err := io.Copy(io.Discard, resp.Body)
		if err != nil {
			results <- FetchResult{
				URL:        resp.Request.URL.String(),
				StatusCode: resp.StatusCode,
				Size:       -1,
				Error:      err,
			}
			continue
		}
		results <- FetchResult{
			URL:        resp.Request.URL.String(),
			StatusCode: resp.StatusCode,
			Size:       int(size),
			Error:      err,
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

	jobs := make(chan string, len(urls))
	results := make(chan FetchResult, len(urls))

	// Start workers
	for w := range numWorkers {
		go worker(w+1, jobs, results)
	}

	// Send jobs
	for i := range urls {
		jobs <- urls[i]
	}
	close(jobs)

	for range urls {
		fmt.Println("Result:", <-results)
	}

	fmt.Println("\nScraping complete!")
}
