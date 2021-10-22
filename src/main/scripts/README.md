| System | Script | Source | Destination | Purpose |
| --- | --- | ---| --- | --- |
| archive | start.sh |  NA | archive-scan | kick off |
| archive | scan.sh | archive-scan | archive-file | reads files from a folder
| archive | size.sh | archive-file | archive-file-size | generates the size and md5 sum of a file
| truenas | size-compare.sh | archive-file-size | archive-file-md5 | might do this, might call it good
| archive | md5.sh | archive-file-md5-request | archive-file-md5 | generates the md5 chksum of a file
