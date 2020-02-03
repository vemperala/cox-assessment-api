
ALTER TABLE location ADD FOREIGN KEY (store_ref) REFERENCES store (id);
ALTER TABLE store_hours ADD FOREIGN KEY (store_ref) REFERENCES store (id);
ALTER TABLE store_services ADD FOREIGN KEY (store_ref) REFERENCES store (id);
ALTER TABLE store_services ADD FOREIGN KEY (service_ref) REFERENCES services (id);
