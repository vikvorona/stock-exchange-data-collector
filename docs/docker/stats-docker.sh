#!/usr/bin/env bash

# Show docker statistics
$(docker stats --format "table {{.Name}}\t{{.CPUPerc}}\t{{.MemPerc}}\t{{.MemUsage}}")
