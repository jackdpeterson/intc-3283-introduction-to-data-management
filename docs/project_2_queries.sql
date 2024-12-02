/**
1. Janice sends out a handwritten "Thank-you" card the first time someone gives to the charity.
  She needs to know who's donated how much on their first donation.
  She also needs to know the mailing address to send the written card to.
  She doesn't want to get any records from donors who don't have a valid mailing address to send the thank-you to since the organization is national and that's going to be a waste of her time to filter that out.
**/


/**
  Janice Query to get donations >= (date of most recent event);

  -- If there was an event between two times (e.g., Janice goes on vacation and didn't run through her thank-you process,
  BETWEEN would be a more appropriate choice to constrain the minimum date range and the maximum date range).

  Finds all donations >= a given date.
  Joins donor information (if available), could add further constraints like WHERE address1 IS NOT NULL and so on for the other fields.
  Groups by Donor ID for aggregation purposes.
  Having constrains the list down to just one donation.

  -- NOTE ... this might need a second query though because that donations.created_at >= 2024-09-10 now means that a previous donor could show up again.
  Getting rid of the date may be appropriate because of this repetition issue. OR ... might want to add another table that tracks the behavior of "letter was actually sent to donor_id".
 */
SELECT donor_id,
       d.first_name,
       d.last_name,
       d.address1,
       d.address2,
       d.city,
       d.state,
       d.zip_code,
       COUNT(donor_id) as donation_count,
       SUM(amount)     as total_donated
FROM donations
         INNER JOIN
     nu_restore.donors d on donations.donor_id = d.id
WHERE address1 <> ''
  AND address2 <> ''
  AND city <> ''
  AND state <> ''
  AND zip_code <> ''
GROUP BY donor_id
HAVING donation_count = 1
   AND total_donated >= 100
ORDER BY donation_count DESC;



/**
  Larry follow-up
 */

# 2. Larry handles donor follow-up.
#   His job is to gather address information from people who gave and only provided a phone-number.
#   His role is twofold: he helps ensure that people can get their IRS Form 8282 for cash donations at the end of the year.
#     He also needs to let Janice know when he's gotten information as well so she can send out a thank-you card.


select donor_id,
       first_name,
       last_name,
       address1,
       address2,
       city,
       state,
       zip_code,
       phone,
       SUM(d.amount)     as total_donated,
       MAX(d.created_at) as last_donated_on
from donors
         RIGHT JOIN nu_restore.donations d on donors.id = d.donor_id
where phone <> ''
  AND (
    address1 = '' OR
    city = '' OR
    state = '' OR
    zip_code = ''
    )
GROUP BY donor_id;



# 3. Jennifer is the CEO of the organization and her main role on a regular basis is to schedule events and needs to know that the total cost of the events doesn't exceed how much is donated overall.
#
#     She uses this information to plan future donation drives -- some of which have an actual cost for renting a facility or staffing the facility (not everyone working here is a volunteer; there are some paid staff).
#
#     Jennifer needs to know how much is donated on a monthly basis as she aims to run one event at the beginning of each month.
#
#     Donations tend to peak at and shortly after an event followed by a lull.
#
#     She needs to know how much is donated by week but isn't concerned about who gave specifically.
#
#   She does; however, need to have a separate report that represents the top 5 donors for any given event so she can give them a personal "thank-you" phone call.
#


## Step #1 - Figure out what is / isn't a query. Top5 is obviously a query.
# The other one is more tricky and needs to be split up and there are a handful of ways to answer Jennifer's question.
# We will, for the sake of simplicity ... assume Jennifer doesn't want to know about anything where there's $0 in donations.


/**
  Jennifer query #1 - Donation Trends; Weekly
 */

SELECT
    DATE_FORMAT(created_at, '%Y-%m-%d') as donated_at,

       SUM(amount) as                   total_donated
FROM donations
WHERE
    created_at BETWEEN '2024-10-07' AND '2024-10-15'
GROUP BY donated_at
ORDER BY donated_at ASC;


/**
  Jennifer Query #2 - Donations trends; Monthly
 */

SELECT DATE_FORMAT(created_at, '%Y-%m') month_donated,
       SUM(amount)
FROM donations
GROUP BY month_donated
ORDER BY month_donated ASC;


/**
  Jennifer Query #3 - Top 5 donors within time window
 */

SELECT
    donor_id,
    SUM(amount) as total_donated
FROM
    donations
WHERE
    created_at BETWEEN :startDate AND :endDate
GROUP BY
    donor_id
ORDER BY
    total_donated DESC
LIMIT 5;








